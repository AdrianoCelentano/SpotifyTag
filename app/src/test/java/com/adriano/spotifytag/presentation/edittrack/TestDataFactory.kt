package com.adriano.spotifytag.presentation.edittrack

import com.adriano.spotifytag.data.database.entity.TrackEntity
import com.spotify.protocol.types.ImageUri

object TestDataFactory {

    fun createTrackViewState(
        uri: String = "uri",
        name: String = "name",
        artist: String = "artist",
        imageUri: ImageUri = ImageUri("uri"),
        album: String = "album"
    ): TrackViewState {
        return TrackViewState(
            uri = uri,
            name = name,
            artist = artist,
            imageUri = imageUri,
            album = album,
        )
    }

    fun createTrackTagData(uri: String = "uri") = listOf(
        TrackEntity(
            uri = uri,
            name = "name",
            artist = "artist",
            album = "album",
            spotifyImageUrl = "image"
        ) to listOf("tagOne, tagTwo")
    )
}