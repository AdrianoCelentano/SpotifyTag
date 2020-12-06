package com.adriano.spotifytag.data.spotify

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.adriano.spotifytag.data.database.repo.TrackRepository
import com.adriano.spotifytag.data.spotify.playlist.SpotifyRetrofitService
import com.adriano.spotifytag.data.spotify.playlist.model.playlist.add.AddTracksBody
import com.adriano.spotifytag.data.spotify.playlist.model.playlist.create.CreatePlaylistBody
import com.adriano.spotifytag.data.spotify.playlist.model.playlist.create.CreatePlaylistResponse
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotifyPlaylistCreator @Inject constructor(
    private val trackRepository: TrackRepository,
    private val spotifyService: SpotifyRetrofitService
) {

    private var loginToken: String? = null
    private var tags: List<String>? = null

    fun createPlaylist(activity: Activity, tags: List<String>) {
        this.tags = tags
        val request = AuthorizationRequest.Builder(
            SpotifyCredentials.ClientId,
            AuthorizationResponse.Type.TOKEN,
            SpotifyCredentials.RedirectUri
        )
            .setShowDialog(false)
            .setScopes(arrayOf("playlist-modify-private"))
            .build()
        AuthorizationClient.openLoginActivity(activity, 100, request)
    }

    fun onLoginResult(resultCode: Int, data: Intent?, requestCode: Int) {
        val response = AuthorizationClient.getResponse(resultCode, data);
        if (requestCode == 100) {
            loginToken = "Bearer ${response.accessToken}"
            ProcessLifecycleOwner.get().lifecycleScope.launch(Dispatchers.IO) {
                val tracks = trackRepository.getTracksWithTags(tags!!)
                val createPlaylistResponse = createPlaylist()
                addTracks(createPlaylistResponse.id, tracks)
            }
        }
    }

    private suspend fun addTracks(
        playlistId: String,
        tracks: List<String>
    ) {
        spotifyService.addTrackToPlaylist(
            authorization = loginToken!!,
            playlistId = playlistId,
            AddTracksBody(tracks)
        )
    }

    private suspend fun createPlaylist(): CreatePlaylistResponse {
        //me user id
        //1123127869
        return spotifyService.createPlaylist(
            authorization = loginToken!!,
            userId = "1123127869",
            body = CreatePlaylistBody(
                name = tags!!.joinToString(separator = "_"),
                description = "tags",
                public = false
            )
        )
    }
}