package com.adriano.spotifytag.edittrack

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adriano.spotifytag.spotify.Spotify
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import timber.log.Timber

class EditTrackViewmodel @ViewModelInject constructor(
    private val spotify: Spotify,
    @ApplicationContext context: Context
) : ViewModel() {

    var state by mutableStateOf(TrackViewState.init())

    val currentTrackFlow = spotify.currentTrackFlow()

    init {
        viewModelScope.launch {
            spotify.connect(context)
        }
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
        }
        updateState(newState)
    }

    private fun handleTextChange(textChangedEvent: TrackViewEvent.TagTextChanged): TrackViewState {
        val newFabState = state.fabState.copy(text = textChangedEvent.value)
        return state.copy(fabState = newFabState)
    }

    private fun updateState(newState: TrackViewState) {
        Timber.d("State: $newState")
        state = newState
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
        return state.fabState.expanded && state.fabState.text.isNotBlank()
    }

    private fun addNewTag(): TrackViewState {
        val currentFabState = state.fabState
        val newFabState = FabState(
            expanded = !currentFabState.expanded,
            text = ""
        )
        val newTags = state.tags.plus(currentFabState.text)
        return state.copy(
            fabState = newFabState,
            tags = newTags,
        )
    }

    private fun toggleFab(): TrackViewState {
        val newFabState = state.fabState.copy(expanded = !state.fabState.expanded)
        return state.copy(fabState = newFabState)
    }

}