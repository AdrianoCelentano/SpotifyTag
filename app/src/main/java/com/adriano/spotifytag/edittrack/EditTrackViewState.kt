package com.adriano.spotifytag.edittrack

import com.spotify.protocol.types.ImageUri

data class EditTrackViewState(
    val tags: List<String>,
    val currentTextInput: String,
    val editMode: Boolean,
    val currentTrack: TrackViewState?
) {
    companion object {
        fun init(): EditTrackViewState {
            return EditTrackViewState(
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
    data class TrackChanged(val track: TrackViewState) : TrackViewEvent()
    data class TagsChanged(val tags: List<String>) : TrackViewEvent()
}

data class TrackViewState(
    val uri: String,
    val name: String,
    val album: String?,
    val artist: String?,
    val imageUri: ImageUri
)