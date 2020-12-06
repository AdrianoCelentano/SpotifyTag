package com.adriano.spotifytag.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.runtime.Providers
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.viewModel
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.*
import com.adriano.spotifytag.data.spotify.SpotifyPlaylistCreator
import com.adriano.spotifytag.data.spotify.player.SpotifyImageLoader
import com.adriano.spotifytag.presentation.createplaylist.CreatePlaylistEffect
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
    lateinit var playlistCreator: SpotifyPlaylistCreator

    @ExperimentalAnimatedInsets
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.Black.toArgb()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            val editTrackViewModel: EditTrackViewModel = viewModel()
            val createPlaylistViewModel: CreatePlaylistViewModel = viewModel()

            observeEffects(createPlaylistViewModel)

            SpotifyTagTheme {
                Providers(AmbientSpotifyImageLoader provides spotifyImageLoader) {
                    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                        NavigationScaffold(editTrackViewModel, createPlaylistViewModel)
                    }
                }
            }
        }
    }

    private fun observeEffects(createPlaylistViewModel: CreatePlaylistViewModel) {
        lifecycleScope.launchWhenCreated {
            val createPlaylistEffect = createPlaylistViewModel.effectChannel.receive()
            when (createPlaylistEffect) {
                is CreatePlaylistEffect.CreatePlaylist -> {
                    playlistCreator.createPlaylist(
                        this@SpotifyTagActivity,
                        createPlaylistEffect.tags
                    )
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        playlistCreator.onLoginResult(resultCode, data, requestCode)
    }
}
