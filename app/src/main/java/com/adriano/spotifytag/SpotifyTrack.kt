package com.adriano.spotifytag

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.asImageAsset
import androidx.ui.tooling.preview.Preview
import com.adriano.spotifytag.ui.SpotifyTagTheme
import com.spotify.protocol.types.ImageUri
import com.spotify.protocol.types.Track
import kotlinx.coroutines.launch

@Composable
fun SpotifyTrackState(track: Track?) {
    if (track == null) Text(text = "empty")
    else TrackView(
        track.artist.name,
        track.name,
        track.album.name,
        track.imageUri
    )
}

@Composable
private fun TrackView(artist: String, name: String, album: String, imageUri: ImageUri) {
    Column {
        Text(text = name)
        SpotifyImage(imageUri)
    }
}

@Composable
private fun SpotifyImage(imageUri: ImageUri) {
    val image = fetchSpotifyImage(imageUri)
    image?.let { Image(asset = it) }
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpotifyTagTheme {
        TrackView(
            name = "Track",
            album = "Album",
            artist = "Artist",
            imageUri = ImageUri("test")
        )
    }
}