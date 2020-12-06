package com.adriano.spotifytag.data.spotify.model

data class Owner(
    val external_urls: ExternalUrlsX,
    val href: String,
    val id: String,
    val type: String,
    val uri: String
)