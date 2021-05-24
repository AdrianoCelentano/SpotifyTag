package com.adriano.spotifytag.presentation.edittrack

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adriano.spotifytag.data.database.TrackTaggingService
import com.adriano.spotifytag.data.database.entity.TrackEntity
import com.adriano.spotifytag.data.spotify.player.TrackObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditTrackViewModel @Inject constructor(
    private val trackObserver: TrackObserver,
    private val trackTaggingService: TrackTaggingService,
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
        trackObserver.disconnect()
    }

    private fun observeCurrentTrack() {
        viewModelScope.launch {
            trackObserver.currentTrackFlow()
                .collect { event(EditTrackViewEvent.TrackChanged(it)) }
        }
    }

    private fun observeTagsForCurrentTrack() {
        viewModelScope.launch {
            trackObserver.currentTrackFlow().collect { track ->
                tagsJob?.cancel()
                tagsJob = viewModelScope.launch {
                    trackTaggingService.getAllTagsForTrack(track.uri)
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
        if (state.editMode.not()) throw IllegalStateException("text change only allowed in edit mode")
        updateState(state.copy(currentTextInput = textChangedEvent.value))
    }

    private suspend fun handleTagClick(tagClickedEvent: EditTrackViewEvent.TagClicked) {
        val currentTrack = state.currentTrack ?: return
        val tagToRemove = state.tags[tagClickedEvent.index]
        trackTaggingService.deleteTagForTrack(tagToRemove, currentTrack.uri)
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
        trackTaggingService.createTagForTrack(
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
