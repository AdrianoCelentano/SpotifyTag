package com.adriano.spotifytag.edittrack

import com.spotify.protocol.types.Track

data class TrackViewState(
    val tags: List<String>,
    val currentTextInput: String,
    val editMode: Boolean,
    val currentTrack: Track?
) {
    companion object {
        fun init(): TrackViewState {
            return TrackViewState(
                currentTextInput = "",
                editMode = false,
                tags = listOf(),
                currentTrack = null,
            )
        }
    }
}

sealed class TrackViewEvent {
    object FabClicked : TrackViewEvent()
    data class TagTextChanged(val value: String) : TrackViewEvent()
    data class TagClicked(val index: Int) : TrackViewEvent()
    data class TrackChanged(val track: Track) : TrackViewEvent()
    data class TagsChanged(val tags: List<String>) : TrackViewEvent()
}