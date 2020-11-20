package com.adriano.spotifytag

import com.spotify.protocol.types.Track
import java.text.FieldPosition

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