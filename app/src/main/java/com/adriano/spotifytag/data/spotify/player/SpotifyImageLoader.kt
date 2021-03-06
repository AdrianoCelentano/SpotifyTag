package com.adriano.spotifytag.data.spotify.player

import android.graphics.Bitmap
import com.spotify.protocol.types.ImageUri

interface SpotifyImageLoader {

    suspend fun loadImage(imageUri: ImageUri): Bitmap
}