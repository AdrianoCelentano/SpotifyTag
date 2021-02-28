package com.adriano.spotifytag.presentation.edittrack.view.track

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.adriano.spotifytag.presentation.LocalSpotifyImageLoader
import com.spotify.protocol.types.ImageUri
import kotlinx.coroutines.launch

@Composable
fun SpotifyImage(imageUri: ImageUri) {
    val image = fetchSpotifyImage(imageUri)
    image?.let {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop,
            bitmap = it,
            contentDescription = "Song Image",
        )
    }
}

@Composable
private fun fetchSpotifyImage(imageUri: ImageUri): ImageBitmap? {

    val coroutineScope = rememberCoroutineScope()
    var image by remember { mutableStateOf<ImageBitmap?>(null) }
    val imageLoader = LocalSpotifyImageLoader.current

    LaunchedEffect(imageUri) {
        coroutineScope.launch {
            val bitmap = imageLoader.loadImage(imageUri)
            image = bitmap.asImageBitmap()
        }
    }

    return image
}
