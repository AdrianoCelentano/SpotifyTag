package com.adriano.spotifytag.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Providers
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.viewModel
import androidx.core.view.WindowCompat
import com.adriano.spotifytag.data.spotify.player.SpotifyAuthenticator
import com.adriano.spotifytag.data.spotify.player.SpotifyImageLoader
import com.adriano.spotifytag.presentation.createplaylist.CreatePlaylistViewModel
import com.adriano.spotifytag.presentation.edittrack.EditTrackViewModel
import com.adriano.spotifytag.presentation.navigation.NavigationScaffold
import com.adriano.spotifytag.presentation.theme.SpotifyTagTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ExperimentalAnimatedInsets
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import javax.inject.Inject

@AndroidEntryPoint
class SpotifyTagActivity : AppCompatActivity() {

    @Inject
    lateinit var spotifyImageLoader: SpotifyImageLoader

    @Inject
    lateinit var spotifyAuthenticator: SpotifyAuthenticator

    @ExperimentalAnimatedInsets
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        spotifyAuthenticator.setActivity(this)

        window.statusBarColor = Color.Black.toArgb()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            val editTrackViewModel: EditTrackViewModel = viewModel()
            val createPlaylistViewModel: CreatePlaylistViewModel = viewModel()

            SpotifyTagTheme {
                Providers(AmbientSpotifyImageLoader provides spotifyImageLoader) {
                    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                        NavigationScaffold(editTrackViewModel, createPlaylistViewModel)
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        spotifyAuthenticator.onLoginResult(resultCode, data, requestCode)
    }

    override fun onStop() {
        super.onStop()
        spotifyAuthenticator.resetActivity()
    }
}
