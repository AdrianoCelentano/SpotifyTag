package com.adriano.spotifytag.edittrack.view.track

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.layout.ContentScale
import com.adriano.spotifytag.SpotifyImageLoaderAmbient
import com.spotify.protocol.types.ImageUri
import kotlinx.coroutines.launch

@Composable
fun SpotifyImage(imageUri: ImageUri) {
    val image = fetchSpotifyImage(imageUri)
    image?.let {
        Image(
            modifier = Modifier.fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop,
            asset = it
        )
    }
}

@Composable
private fun fetchSpotifyImage(imageUri: ImageUri): ImageAsset? {

    val coroutineScope = rememberCoroutineScope()
    var image by remember { mutableStateOf<ImageAsset?>(null) }
    val imageLoader = SpotifyImageLoaderAmbient.current

    onCommit(imageUri) {
        coroutineScope.launch {
            val bitmap = imageLoader.loadImage(imageUri)
            image = bitmap.asImageAsset()
        }
    }

    return image
}