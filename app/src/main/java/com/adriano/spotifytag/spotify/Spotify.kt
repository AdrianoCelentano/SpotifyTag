package com.adriano.spotifytag.spotify

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.adriano.spotifytag.edittrack.TrackViewState
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.types.ImageUri
import com.spotify.protocol.types.PlayerState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Spotify @Inject constructor(
    @ApplicationContext private val context: Context
) : SpotifyImageLoader {

    private lateinit var spotifyAppRemote: SpotifyAppRemote
    private val playerStateFlow = MutableSharedFlow<PlayerState>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        ProcessLifecycleOwner.get().lifecycleScope.launch {
            connect()
        }
    }

    private suspend fun connect() = suspendCancellableCoroutine<Unit> { continuation ->

        if (isConnected()) continuation.resumeWith(Result.success(Unit))

        SpotifyAppRemote.connect(
            context, connectionParams(),
            object : Connector.ConnectionListener {
                override fun onConnected(spotifyAppRemote: SpotifyAppRemote) {
                    this@Spotify.spotifyAppRemote = spotifyAppRemote
                    subscribeToPlayerState()
                    continuation.resumeWith(Result.success(Unit))
                }

                override fun onFailure(throwable: Throwable) {
                    if (continuation.isCompleted) Timber.e(throwable)
                    else continuation.resumeWith(Result.failure(throwable))
                }
            }
        )
    }

    fun disconnect() {
        if (isConnected()) SpotifyAppRemote.disconnect(spotifyAppRemote)
    }

    override suspend fun loadImage(imageUri: ImageUri) =
        suspendCancellableCoroutine<Bitmap> { continuation ->
            spotifyAppRemote.imagesApi.getImage(imageUri).setResultCallback { bitmap ->
                continuation.resumeWith(Result.success(bitmap))
            }
        }

    fun currentTrackFlow(): Flow<TrackViewState> {
        return playerStateFlow
            .map { it.track }
            .map { track ->
                TrackViewState(
                    uri = track.uri,
                    name = track.name,
                    artist = track.artist.name,
                    album = track.album.name,
                    imageUri = track.imageUri,
                )
            }
    }

    private fun subscribeToPlayerState() {
        errorIfPlayerIsNotConnected()
        spotifyAppRemote.playerApi
            .subscribeToPlayerState()
            .setEventCallback { playerState ->
                playerStateFlow.tryEmit(playerState)
            }
    }

    private fun connectionParams(): ConnectionParams? {
        return ConnectionParams
            .Builder(SpotifyCredentials.ClientId)
            .setRedirectUri(SpotifyCredentials.RedirectUri)
            .showAuthView(true)
            .build()
    }

    private fun errorIfPlayerIsNotConnected() {
        if (::spotifyAppRemote.isInitialized.not()) throw IllegalStateException("Spotify no connected")
    }

    private fun isConnected() = ::spotifyAppRemote.isInitialized && spotifyAppRemote.isConnected
}
