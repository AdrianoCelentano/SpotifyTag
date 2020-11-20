package com.adriano.spotifytag

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

class MainViewmodel @ViewModelInject constructor(
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

    private fun handleTagClick(trackViewEvent: TrackViewEvent.TagClicked): TrackViewState {
        val newTags = state.tags.toMutableList()
        newTags.removeAt(trackViewEvent.index)
        return state.copy(tags = newTags)
    }

    private fun handleFabClick(): TrackViewState {
        return if (state.fabExpanded) {
            state.copy(
                fabExpanded = !state.fabExpanded,
                tags = addNewTag(),
                newTagText = ""
            )
        } else state.copy(fabExpanded = !state.fabExpanded)
    }

    private fun updateState(newState: TrackViewState) {
        Timber.d("State: $newState")
        state = newState
    }

    private fun addNewTag(): List<String> {
        if (state.newTagText.isBlank()) return state.tags
        return state.tags + state.newTagText
    }
}