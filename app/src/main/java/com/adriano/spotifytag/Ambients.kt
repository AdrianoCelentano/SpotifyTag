package com.adriano.spotifytag

import androidx.compose.runtime.staticAmbientOf
import com.adriano.spotifytag.data.spotify.SpotifyImageLoader

val SpotifyImageLoaderAmbient = staticAmbientOf<SpotifyImageLoader> { error("Spotify not provided") }