package com.adriano.spotifytag.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.adriano.spotifytag.data.spotify.player.SpotifyAuthenticator
import com.adriano.spotifytag.data.spotify.player.SpotifyImageLoader
import com.adriano.spotifytag.presentation.createplaylist.CreatePlaylistEffect
import com.adriano.spotifytag.presentation.createplaylist.CreatePlaylistViewModel
import com.adriano.spotifytag.presentation.edittrack.EditTrackViewModel
import com.adriano.spotifytag.presentation.navigation.NavigationScaffold
import com.adriano.spotifytag.presentation.theme.SpotifyTagTheme
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ExperimentalAnimatedInsets
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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
            observeEffects(createPlaylistViewModel)
            SpotifyTagTheme {
                CompositionLocalProvider(LocalSpotifyImageLoader provides spotifyImageLoader) {
                    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                        NavigationScaffold(editTrackViewModel, createPlaylistViewModel)
                    }
                }
            }
        }
    }

    private fun observeEffects(createPlaylistViewModel: CreatePlaylistViewModel) {
        lifecycleScope.launch(Dispatchers.Main) {
            createPlaylistViewModel.effectFlow.collect { effect ->
                when (effect) {
                    is CreatePlaylistEffect.OpenSpotify -> {
                        openPlaylist(effect.uri)
                    }
                    is CreatePlaylistEffect.ErrorToast -> {
                        showToast(effect.message)
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            this@SpotifyTagActivity,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun openPlaylist(uri: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(uri)
        intent.putExtra(
            Intent.EXTRA_REFERRER,
            Uri.parse("android-app://$packageName")
        )
        startActivity(intent)
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
