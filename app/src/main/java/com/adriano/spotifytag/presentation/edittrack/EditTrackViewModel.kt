package com.adriano.spotifytag.presentation.edittrack

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adriano.spotifytag.data.database.entity.TrackEntity
import com.adriano.spotifytag.data.database.repo.TagRepository
import com.adriano.spotifytag.data.spotify.player.SpotifyPlayerObserver
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class EditTrackViewModel @ViewModelInject constructor(
    private val spotify: SpotifyPlayerObserver,
    private val tagRepository: TagRepository,
) : ViewModel() {

    private var tagsJob: Job? = null
    var state by mutableStateOf(EditTrackViewState.init())

    init {
        observeCurrentTrack()
        observeTagsForCurrentTrack()
    }

    fun event(trackViewEvent: EditTrackViewEvent) {
        Timber.d("Event: $trackViewEvent")
        viewModelScope.launch {
            try {
                when (trackViewEvent) {
                    EditTrackViewEvent.FabClicked -> handleFabClick()
                    is EditTrackViewEvent.TagTextChanged -> handleTextChange(trackViewEvent)
                    is EditTrackViewEvent.TagClicked -> handleTagClick(trackViewEvent)
                    is EditTrackViewEvent.TrackChanged -> handleTrackChanged(trackViewEvent)
                    is EditTrackViewEvent.TagsChanged -> handleTagsChanged(trackViewEvent)
                }
            } catch (throwable: Throwable) {
                Timber.e(throwable)
            }
        }
    }

    private fun updateState(newState: EditTrackViewState) {
        Timber.d("State: $newState")
        state = newState
    }

    override fun onCleared() {
        super.onCleared()
        spotify.disconnect()
    }

    private fun observeCurrentTrack() {
        viewModelScope.launch {
            spotify.currentTrackFlow()
                .collect { event(EditTrackViewEvent.TrackChanged(it)) }
        }
    }

    private fun observeTagsForCurrentTrack() {
        viewModelScope.launch {
            spotify.currentTrackFlow().collect { track ->
                tagsJob?.cancel()
                tagsJob = viewModelScope.launch {
                    tagRepository.getAllTagsForTrack(track.uri)
                        .collect { event(EditTrackViewEvent.TagsChanged(it)) }
                }
            }
        }
    }

    private fun handleTagsChanged(trackViewEvent: EditTrackViewEvent.TagsChanged) {
        updateState(
            state.copy(
                editMode = false,
                currentTextInput = "",
                tags = trackViewEvent.tags,
            )
        )
    }

    private fun handleTrackChanged(trackChangedEvent: EditTrackViewEvent.TrackChanged) {
        updateState(state.copy(currentTrack = trackChangedEvent.track))
    }

    private fun handleTextChange(textChangedEvent: EditTrackViewEvent.TagTextChanged) {
        updateState(state.copy(currentTextInput = textChangedEvent.value))
    }

    private suspend fun handleTagClick(tagClickedEvent: EditTrackViewEvent.TagClicked) {
        val currentTrack = state.currentTrack ?: return
        val tagToRemove = state.tags[tagClickedEvent.index]
        tagRepository.deleteTagForTrack(tagToRemove, currentTrack.uri)
    }

    private suspend fun handleFabClick() {
        if (shouldAddTag()) addNewTag()
        toggleFab()
    }

    private fun shouldAddTag(): Boolean {
        return state.editMode && state.currentTextInput.isNotBlank()
    }

    private suspend fun addNewTag() {
        val currentTrack = state.currentTrack ?: return
        tagRepository.createTagForTrack(
            trackEntity = TrackEntity(
                name = currentTrack.name,
                artist = currentTrack.artist,
                album = currentTrack.album,
                uri = currentTrack.uri,
                spotifyImageUrl = currentTrack.imageUri.raw
            ),
            tag = state.currentTextInput
        )
    }

    private fun toggleFab() {
        updateState(state.copy(editMode = !state.editMode))
    }
}
