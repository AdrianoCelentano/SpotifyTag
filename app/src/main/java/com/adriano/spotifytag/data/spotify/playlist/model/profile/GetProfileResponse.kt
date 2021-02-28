package com.adriano.spotifytag.data.spotify.playlist.model.profile

data class GetProfileResponse(
    val country: String,
    val display_name: String,
    val email: String,
    val external_urls: ExternalUrls,
    val followers: Followers,
    val href: String,
    val id: String,
    val images: List<Image>,
    val product: String,
    val type: String,
    val uri: String
)