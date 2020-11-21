package com.adriano.spotifytag.edittrack

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adriano.spotifytag.spotify.Spotify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class EditTrackViewModel @ViewModelInject constructor(
    private val spotify: Spotify
) : ViewModel() {

    var state by mutableStateOf(TrackViewState.init())

    init {
        observeSpotifyTrack()
    }

    override fun onCleared() {
        super.onCleared()
        spotify.disconnect()
    }

    fun event(trackViewEvent: TrackViewEvent) {
        Timber.d("Event: $trackViewEvent")
        val newState: TrackViewState = when (trackViewEvent) {
            TrackViewEvent.FabClicked -> handleFabClick()
            is TrackViewEvent.TagTextChanged -> handleTextChange(trackViewEvent)
            is TrackViewEvent.TagClicked -> handleTagClick(trackViewEvent)
            is TrackViewEvent.TrackChanged -> handleTrackChanged(trackViewEvent)
        }
        updateState(newState)
    }

    private fun updateState(newState: TrackViewState) {
        Timber.d("State: $newState")
        state = newState
    }

    private fun observeSpotifyTrack() {
        viewModelScope.launch {
            spotify.connect()
            spotify.currentTrackFlow()
                .collect { event(TrackViewEvent.TrackChanged(it)) }
        }
    }

    private fun handleTrackChanged(trackChangedEvent: TrackViewEvent.TrackChanged): TrackViewState {
        return state.copy(currentTrack = trackChangedEvent.track)
    }

    private fun handleTextChange(textChangedEvent: TrackViewEvent.TagTextChanged): TrackViewState {
        return state.copy(currentTextInput = textChangedEvent.value)
    }

    private fun handleTagClick(tagClickedEvent: TrackViewEvent.TagClicked): TrackViewState {
        val tagToRemove = state.tags[tagClickedEvent.index]
        val newTags = state.tags.minus(tagToRemove)
        return state.copy(tags = newTags)
    }

    private fun handleFabClick(): TrackViewState {
        return if (shouldAddTag()) addNewTag()
        else toggleFab()
    }

    private fun shouldAddTag(): Boolean {
        return state.editMode && state.currentTextInput.isNotBlank()
    }

    private fun addNewTag(): TrackViewState {
        val newTags = state.tags.plus(state.currentTextInput)
        return state.copy(
            editMode = false,
            currentTextInput = "",
            tags = newTags,
        )
    }

    private fun toggleFab(): TrackViewState {
        return state.copy(editMode = !state.editMode)
    }

}