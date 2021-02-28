package com.adriano.spotifytag.presentation.createplaylist

sealed class CreatePlaylistEffect {
    data class OpenSpotify(val uri: String) : CreatePlaylistEffect()
    data class ErrorToast(val message: String) : CreatePlaylistEffect()
}
