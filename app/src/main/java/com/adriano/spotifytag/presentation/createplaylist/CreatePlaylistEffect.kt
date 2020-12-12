package com.adriano.spotifytag.presentation.createplaylist

sealed class CreatePlaylistEffect {
    object UpdateToken : CreatePlaylistEffect()
}