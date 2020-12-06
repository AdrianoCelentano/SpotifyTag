package com.adriano.spotifytag.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.viewinterop.viewModel
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.adriano.spotifytag.SpotifyImageLoaderAmbient
import com.adriano.spotifytag.data.spotify.SpotifyImageLoader
import com.adriano.spotifytag.presentation.createplaylist.CreatePlaylistViewModel
import com.adriano.spotifytag.presentation.createplaylist.view.CreatePlaylistView
import com.adriano.spotifytag.presentation.edittrack.EditTrackViewModel
import com.adriano.spotifytag.presentation.edittrack.view.EditTrackView
import com.adriano.spotifytag.presentation.theme.SpotifyTagTheme
import com.spotify.sdk.android.auth.AuthorizationClient
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.accompanist.insets.ExperimentalAnimatedInsets
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import javax.inject.Inject

@AndroidEntryPoint
class SpotifyTagActivity : AppCompatActivity() {

    @Inject
    lateinit var spotifyImageLoader: SpotifyImageLoader

    @ExperimentalAnimatedInsets
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = Color.Black.toArgb()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

            val navController = rememberNavController()
            val editTrackViewModel: EditTrackViewModel = viewModel()
            val createPlaylistViewModel: CreatePlaylistViewModel = viewModel()

            SpotifyTagTheme {
                Providers(SpotifyImageLoaderAmbient provides spotifyImageLoader) {
                    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                        Scaffold(
                            bottomBar = {
                                SpotifyTagBottomNavigation(navController)
                            }
                        ) { innerPadding ->
                            NavHost(navController, startDestination = Screen.EditTrack.route) {
                                composable(Screen.EditTrack.route) {
                                    EditTrackView(innerPadding, editTrackViewModel)
                                }
                                composable(Screen.CreatePlaylist.route) {
                                    CreatePlaylistView(innerPadding, createPlaylistViewModel)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val response = AuthorizationClient.getResponse(resultCode, data);
        if (requestCode == 100) {
            response.accessToken
        }
    }
}

@Composable
private fun SpotifyTagBottomNavigation(
    navController: NavHostController,
) {
    val items = listOf(
        Screen.EditTrack,
        Screen.CreatePlaylist,
    )

    BottomNavigation(
        modifier = Modifier.navigationBarsPadding(),
        backgroundColor = Color.Black,
        contentColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.popBackStack(navController.graph.startDestination, false)
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route)
                    }
                }
            )
        }
    }
}


sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object EditTrack : Screen("EditTags", "Add Tags", Icons.Filled.Add)
    object CreatePlaylist : Screen("CreatePlaylist", "Create Playlist", Icons.Filled.PlayArrow)
}
