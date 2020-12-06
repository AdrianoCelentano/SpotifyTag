package com.adriano.spotifytag.data.spotify.model

data class CreatePlaylistResponse(
    val collaborative: Boolean,
    val description: Any,
    val external_urls: ExternalUrls,
    val followers: Followers,
    val href: String,
    val id: String,
    val images: List<Any>,
    val name: String,
    val owner: Owner,
    val `public`: Boolean,
    val snapshot_id: String,
    val tracks: Tracks,
    val type: String,
    val uri: String
)