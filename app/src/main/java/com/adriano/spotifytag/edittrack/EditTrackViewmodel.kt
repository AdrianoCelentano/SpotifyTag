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
            is TrackViewEvent.TagTextChanged -> state.copy(newTagText = trackViewEvent.value)
            is TrackViewEvent.TagClicked -> handleTagClick(trackViewEvent)
        }
        updateState(newState)
    }

    private fun updateState(newState: TrackViewState) {
        Timber.d("State: $newState")
        state = newState
    }

    private fun handleTagClick(trackViewEvent: TrackViewEvent.TagClicked): TrackViewState {
        val tagToRemove = state.tags[trackViewEvent.index]
        val newTags = state.tags.minus(tagToRemove)
        return state.copy(tags = newTags)
    }

    private fun handleFabClick(): TrackViewState {
        return if (shouldAddTag()) addNewTag()
        else toggleFab()
    }

    private fun shouldAddTag() = state.fabExpanded && state.newTagText.isNotBlank()

    private fun addNewTag(): TrackViewState {
        return state.copy(
            fabExpanded = !state.fabExpanded,
            tags = state.tags.plus(state.newTagText),
            newTagText = state.newTagText
        )
    }

    private fun toggleFab() = state.copy(fabExpanded = !state.fabExpanded)

}