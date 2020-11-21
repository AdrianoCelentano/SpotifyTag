package com.adriano.spotifytag.edittrack.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adriano.spotifytag.edittrack.view.tags.TagsLayout
import com.adriano.spotifytag.edittrack.view.track.SpotifyTrackCard
import com.spotify.protocol.types.Track

@Composable
fun TrackAndTagsColumn(
    modifier: Modifier = Modifier,
    tags: List<String>,
    onTagClicked: (Int) -> Unit,
    track: Track?,
    scaleFactor: Float
) {
    Log.d("qwer", "scale: $scaleFactor")
    Column(
        modifier = modifier
    )
    {
        SpotifyTrackCard(
            modifier = Modifier.fillMaxWidth(fraction = scaleFactor)
                .padding(24.dp)
                .align(Alignment.CenterHorizontally),
            scaleFactor,
            track = track
        )
        TagsLayout(
            modifier = Modifier.fillMaxSize()
                .padding(bottom = 80.dp),
            tags = tags,
            onTagClicked = onTagClicked
        )
    }
}