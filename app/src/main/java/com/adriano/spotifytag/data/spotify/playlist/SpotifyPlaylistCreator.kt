package com.adriano.spotifytag.data.spotify.playlist

import com.adriano.spotifytag.data.database.repo.TrackRepository
import com.adriano.spotifytag.data.spotify.player.SpotifyAuthenticator
import com.adriano.spotifytag.data.spotify.playlist.model.playlist.add.AddTracksBody
import com.adriano.spotifytag.data.spotify.playlist.model.playlist.create.CreatePlaylistBody
import com.adriano.spotifytag.data.spotify.playlist.model.playlist.create.CreatePlaylistResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotifyPlaylistCreator @Inject constructor(
    private val trackRepository: TrackRepository,
    private val spotifyService: SpotifyRetrofitService,
    private val spotifyAuthenticator: SpotifyAuthenticator,
) {

    suspend fun createPlaylist(tags: List<String>): String? {
        val token = spotifyAuthenticator.getToken() ?: return null
        val tracks = trackRepository.getTracksWithTags(tags)
        val createPlaylistResponse = createPlaylistWithTags(token, tags)
        addTracks(token, createPlaylistResponse.id, tracks)
        return createPlaylistResponse.uri
    }

    private suspend fun addTracks(
        token: String,
        playlistId: String,
        tracks: List<String>
    ) {
        spotifyService.addTrackToPlaylist(
            authorization = token,
            playlistId = playlistId,
            AddTracksBody(tracks)
        )
    }

    private suspend fun createPlaylistWithTags(
        token: String,
        tags: List<String>
    ): CreatePlaylistResponse {
        val profile = spotifyService.getProfile(authorization = token)
        return spotifyService.createPlaylist(
            authorization = token,
            userId = profile.id,
            body = CreatePlaylistBody(
                name = tags.joinToString(separator = "_"),
                description = "tags",
                public = false
            )
        )
    }
}