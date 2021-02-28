package com.adriano.spotifytag.data.spotify.playlist.model.playlist.create

data class CreatePlaylistBody(
    val name: String,
    val description: String,
    val public: Boolean
)
