package com.adriano.spotifytag.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.adriano.spotifytag.presentation.createplaylist.CreatePlaylistViewModel
import com.adriano.spotifytag.presentation.createplaylist.view.CreatePlaylistView
import com.adriano.spotifytag.presentation.edittrack.EditTrackViewModel
import com.adriano.spotifytag.presentation.edittrack.view.EditTrackView
import dev.chrisbanes.accompanist.insets.navigationBarsPadding

@Composable
fun NavigationScaffold(
    editTrackViewModel: EditTrackViewModel,
    createPlaylistViewModel: CreatePlaylistViewModel
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            SpotifyTagBottomNavigation(navController)
        }
    ) { innerPadding ->
        NavGraph(navController, innerPadding, editTrackViewModel, createPlaylistViewModel)
    }
}

@Composable
private fun NavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    editTrackViewModel: EditTrackViewModel,
    createPlaylistViewModel: CreatePlaylistViewModel
) {
    NavHost(navController, startDestination = Screen.EditTrack.route) {
        composable(Screen.EditTrack.route) {
            EditTrackView(innerPadding, editTrackViewModel)
        }
        composable(Screen.CreatePlaylist.route) {
            CreatePlaylistView(innerPadding, createPlaylistViewModel)
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


private sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object EditTrack : Screen("EditTags", "Add Tags", Icons.Filled.Add)
    object CreatePlaylist : Screen("CreatePlaylist", "Create Playlist", Icons.Filled.PlayArrow)
}