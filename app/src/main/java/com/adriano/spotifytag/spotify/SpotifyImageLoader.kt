package com.adriano.spotifytag.spotify

import android.graphics.Bitmap
import com.spotify.protocol.types.ImageUri

interface SpotifyImageLoader {

    suspend fun loadImage(imageUri: ImageUri): Bitmap
}