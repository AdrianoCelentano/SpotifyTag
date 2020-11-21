package com.adriano.spotifytag.edittrack.view.track

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.AmbientContentAlpha
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.adriano.spotifytag.theme.typography
import com.spotify.protocol.types.ImageUri
import com.spotify.protocol.types.Track

@Composable
fun SpotifyTrackCard(modifier: Modifier = Modifier, track: Track?) {
    Card(
        modifier = modifier,
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

@Composable
private fun EmptyView() {
    Box(modifier = Modifier) {
        Text(text = "Connecting to Spotify", modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun TrackView(
    artist: String,
    name: String,
    album: String,
    imageUri: ImageUri
) {
    Column {
        SpotifyImage(imageUri)
        Spacer(Modifier.height(12.dp))
        Providers(AmbientContentAlpha provides ContentAlpha.high) {
            TrackText(typography.body1, name)
        }
        Spacer(Modifier.height(4.dp))
        Providers(AmbientContentAlpha provides ContentAlpha.medium) {
            TrackText(typography.body2, artist)
            Spacer(Modifier.height(2.dp))
            TrackText(typography.body2, album)
        }
        Spacer(Modifier.height(12.dp))
    }
}

@Composable
private fun TrackText(style: TextStyle, text: String) {
    Text(
        style = style,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp),
        text = text,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
        maxLines = 1
    )
}