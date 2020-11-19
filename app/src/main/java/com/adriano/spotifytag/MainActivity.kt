package com.adriano.spotifytag

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.platform.setContent
import androidx.ui.tooling.preview.Preview
import com.adriano.spotifytag.spotify.Spotify
import com.adriano.spotifytag.spotify.SpotifyImageLoader
import com.adriano.spotifytag.ui.SpotifyTagTheme
import com.spotify.protocol.types.ImageUri
import com.spotify.protocol.types.Track
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO map spotify to UI Model

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewmodel: MainViewmodel by viewModels()

    @Inject
    lateinit var spotifyImageLoader: SpotifyImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            SpotifyTagTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val currentTrack = mainViewmodel.currentTrackFlow.collectAsState(initial = null)
                    Providers(SpotifyImageLoaderAmbient provides spotifyImageLoader) {
                        SpotifyTrackState(currentTrack.value)
                    }
                }
            }
        }
    }
}