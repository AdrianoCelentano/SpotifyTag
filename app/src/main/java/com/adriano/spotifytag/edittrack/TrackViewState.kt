package com.adriano.spotifytag.edittrack

import com.spotify.protocol.types.Track

data class TrackViewState(
    val newTagText: String,
    val fabExpanded: Boolean,
    val tags: List<String>,
    val currentTrack: Track?
) {
    companion object {
        fun init(): TrackViewState {
            return TrackViewState(
                newTagText = "",
                fabExpanded = false,
                tags = listOf("piano", "slow", "classic"),
                currentTrack = null
            )
        }
    }
}

sealed class TrackViewEvent {
    object FabClicked: TrackViewEvent()
    data class TagTextChanged(val value: String): TrackViewEvent()
    data class TagClicked(val index: Int): TrackViewEvent()
}