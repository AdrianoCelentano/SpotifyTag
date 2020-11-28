package com.adriano.spotifytag.presentation.edittrack

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

data class TrackViewState(
    val uri: String,
    val name: String,
    val album: String?,
    val artist: String?,
    val imageUri: ImageUri
)