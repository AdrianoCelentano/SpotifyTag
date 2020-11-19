package com.adriano.spotifytag.spotify

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.protocol.client.Subscription
import com.spotify.protocol.types.ImageUri
import com.spotify.protocol.types.PlayerState
import com.spotify.protocol.types.Track
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Spotify @Inject constructor(): SpotifyImageLoader {

    private var playerStateSubscription: Subscription<PlayerState>? = null
    private lateinit var spotifyAppRemote: SpotifyAppRemote
    private val playerStateFlow = MutableSharedFlow<PlayerState>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    suspend fun connect(
        context: Context,
    ) = suspendCancellableCoroutine<Unit> { continuation ->

        if (isConnected()) continuation.resumeWith(Result.success(Unit))

        SpotifyAppRemote.connect(context, connectionParams(),
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
            })
    }

    fun disconnect() {
        if (isConnected()) SpotifyAppRemote.disconnect(spotifyAppRemote)
    }

    override suspend fun loadImage(imageUri: ImageUri) = suspendCancellableCoroutine<Bitmap> { continuation ->
        spotifyAppRemote.imagesApi.getImage(imageUri).setResultCallback { bitmap ->
            continuation.resumeWith(Result.success(bitmap))
        }
    }

    fun currentTrackFlow(): Flow<Track> {
        return playerStateFlow
            .map { it.track }
    }

    private fun subscribeToPlayerState() {
        errorIfPlayerIsNotConnected()
        playerStateSubscription = spotifyAppRemote.playerApi
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
