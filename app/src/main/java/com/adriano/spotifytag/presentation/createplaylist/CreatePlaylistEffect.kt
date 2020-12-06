package com.adriano.spotifytag.presentation.createplaylist

sealed class CreatePlaylistEffect {
    data class CreatePlaylist(val tags: List<String>) : CreatePlaylistEffect()
}