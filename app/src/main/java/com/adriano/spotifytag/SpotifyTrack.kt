package com.adriano.spotifytag

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.AmbientContentAlpha
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.graphics.asImageAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.adriano.spotifytag.ui.SpotifyTagTheme
import com.adriano.spotifytag.ui.typography
import com.spotify.protocol.types.ImageUri
import com.spotify.protocol.types.Track
import kotlinx.coroutines.launch

@Composable
fun SpotifyTrackState(modifier: Modifier = Modifier, track: Track?) {
    Box(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier.align(Alignment.Center).fillMaxWidth(fraction = 0.6f),
            border = BorderStroke(1.dp, Color.Gray),
            elevation = 2.dp,
        ) {
            if (track == null) EmptyView()
            else TrackView(
                artist = track.artist.name,
                name = track.name,
                album = track.album.name,
                imageUri = track.imageUri
            )
        }
    }
}

@Composable
private fun EmptyView() {
    Box(modifier = Modifier) {
        Text(text = "Connecting to Spotify", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun TrackView(
    modifier: Modifier = Modifier,
    artist: String,
    name: String,
    album: String,
    imageUri: ImageUri
) {
    Column() {
        SpotifyImage(imageUri)
        Spacer(Modifier.height(4.dp))
        Providers(AmbientContentAlpha provides ContentAlpha.high) {
            TrackText(typography.body1, name)
        }
        Spacer(Modifier.height(2.dp))
        Providers(AmbientContentAlpha provides ContentAlpha.medium) {
            TrackText(typography.body2, artist)
            TrackText(typography.body2, album)
        }
        Spacer(Modifier.height(4.dp))
    }
}

@Composable
private fun TrackText(style: TextStyle, text: String) {
    Text(
        style = style,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
        text = text,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
        maxLines = 1
    )
}

@Composable
private fun SpotifyImage(imageUri: ImageUri) {
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