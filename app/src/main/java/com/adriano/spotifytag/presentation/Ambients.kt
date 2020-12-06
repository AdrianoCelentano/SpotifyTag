package com.adriano.spotifytag.presentation

import androidx.compose.runtime.staticAmbientOf
import com.adriano.spotifytag.data.spotify.player.SpotifyImageLoader

val AmbientSpotifyImageLoader =
    staticAmbientOf<SpotifyImageLoader> { error("Spotify not provided") }