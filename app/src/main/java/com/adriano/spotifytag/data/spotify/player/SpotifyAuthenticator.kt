package com.adriano.spotifytag.data.spotify.player

import android.app.Activity
import android.content.Intent
import com.adriano.spotifytag.data.spotify.SpotifyCredentials
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.coroutines.channels.Channel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpotifyAuthenticator @Inject constructor() {

    private var currentActivity: Activity? = null
    private var currentToken: String? = null
    private val tokenChannel = Channel<String>()

    suspend fun getToken(): String? {
        return currentToken ?: updateToken()
    }

    suspend fun updateToken(): String? {
        if (currentActivity == null) return null
        val request = AuthorizationRequest.Builder(
            SpotifyCredentials.ClientId,
            AuthorizationResponse.Type.TOKEN,
            SpotifyCredentials.RedirectUri
        )
            .setShowDialog(false)
            .setScopes(arrayOf("playlist-modify-private"))
            .build()
        AuthorizationClient.openLoginActivity(currentActivity, 100, request)
        currentToken = tokenChannel.receive()
        return currentToken
    }

    fun onLoginResult(resultCode: Int, data: Intent?, requestCode: Int) {
        val response = AuthorizationClient.getResponse(resultCode, data);
        if (requestCode == 100) {
            tokenChannel.offer("Bearer ${response.accessToken}")
        }
    }

    fun setActivity(activity: Activity) {
        currentActivity = activity
    }

    fun resetActivity() {
        currentActivity = null
    }

}