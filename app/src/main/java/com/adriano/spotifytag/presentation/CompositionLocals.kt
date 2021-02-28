package com.adriano.spotifytag.presentation

import androidx.compose.runtime.compositionLocalOf
import com.adriano.spotifytag.data.spotify.player.SpotifyImageLoader

val LocalSpotifyImageLoader =
    compositionLocalOf<SpotifyImageLoader> { error("Spotify not provided") }
