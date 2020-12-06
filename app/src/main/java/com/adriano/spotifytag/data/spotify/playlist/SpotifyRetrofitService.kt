package com.adriano.spotifytag.data.spotify.playlist

import com.adriano.spotifytag.data.spotify.playlist.model.playlist.add.AddTracksBody
import com.adriano.spotifytag.data.spotify.playlist.model.playlist.create.CreatePlaylistBody
import com.adriano.spotifytag.data.spotify.playlist.model.playlist.create.CreatePlaylistResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface SpotifyRetrofitService {
    @POST("users/{user_id}/playlists")
    suspend fun createPlaylist(
        @Header("Authorization") authorization: String,
        @Path("user_id") userId: String,
        @Body body: CreatePlaylistBody
    ): CreatePlaylistResponse

    @POST("playlists/{playlist_id}/tracks")
    suspend fun addTrackToPlaylist(
        @Header("Authorization") authorization: String,
        @Path("playlist_id") playlistId: String,
        @Body body: AddTracksBody
    )
}