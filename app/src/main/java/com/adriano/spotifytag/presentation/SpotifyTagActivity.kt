package com.adriano.spotifytag.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Providers
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.setContent
import com.adriano.spotifytag.SpotifyImageLoaderAmbient
import com.adriano.spotifytag.data.spotify.SpotifyImageLoader
import com.adriano.spotifytag.presentation.createplaylist.view.CreatePlaylistView
import com.adriano.spotifytag.presentation.theme.SpotifyTagTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import javax.inject.Inject

@AndroidEntryPoint
class SpotifyTagActivity : AppCompatActivity() {

    @Inject
    lateinit var spotifyImageLoader: SpotifyImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.Black.toArgb()

        setContent {
            SpotifyTagTheme {
                Providers(SpotifyImageLoaderAmbient provides spotifyImageLoader) {
                    ProvideWindowInsets {
                        CreatePlaylistView()
                    }
                }
            }
        }
    }
}