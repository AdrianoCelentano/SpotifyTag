package com.adriano.spotifytag.edittrack

import com.spotify.protocol.types.Track

data class TrackViewState(
    val fabState: FabState,
    val tags: List<String>,
    val currentTrack: Track?
) {
    companion object {
        fun init(): TrackViewState {
            return TrackViewState(
                fabState = FabState(
                    text = "",
                    expanded = false,
                ),
                tags = listOf("piano", "slow", "classic"),
                currentTrack = null,
            )
        }
    }
}

data class FabState(
    val text: String,
    val expanded: Boolean
)

sealed class TrackViewEvent {
    object FabClicked : TrackViewEvent()
    data class TagTextChanged(val value: String) : TrackViewEvent()
    data class TagClicked(val index: Int) : TrackViewEvent()
    data class TrackChanged(val track: Track) : TrackViewEvent()
}