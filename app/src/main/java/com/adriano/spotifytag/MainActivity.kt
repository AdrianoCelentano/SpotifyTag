package com.adriano.spotifytag

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Providers
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import com.adriano.spotifytag.spotify.SpotifyImageLoader
import com.adriano.spotifytag.ui.SpotifyTagTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
                        SpotifyTrackState(
                            track = currentTrack.value,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}