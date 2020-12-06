package com.adriano.spotifytag.data.spotify

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject

class PlaylistCreator @Inject constructor(

) {

    private var spotifyService: SpotifyRetrofitService
    private var loginToken: String? = null

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spotify.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        spotifyService = retrofit.create(SpotifyRetrofitService::class.java)
    }

    fun createPlaylist(activity: Activity) {
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
            ProcessLifecycleOwner.get().lifecycleScope.launch {
                createPlaylist()
            }
        }
    }

    private suspend fun createPlaylist() {
        //me user id
        //1123127869
        spotifyService.createPlaylist(
            authorization = loginToken!!,
            userId = "1123127869",
            body = CreatePlaylistBody(
                name = "NewPlaylist",
                description = "tags",
                public = false
            )
        )
    }
}

interface SpotifyRetrofitService {
    @POST("users/{user_id}/playlists")
    suspend fun createPlaylist(
        @Header("Authorization") authorization: String,
        @Path("user_id") userId: String,
        @Body body: CreatePlaylistBody
    )
}

data class CreatePlaylistBody(
    val name: String,
    val description: String,
    val public: Boolean
)