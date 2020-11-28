package com.adriano.spotifytag.presentation.edittrack.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adriano.spotifytag.presentation.edittrack.TrackViewState
import com.adriano.spotifytag.presentation.edittrack.view.tags.TagsLayout
import com.adriano.spotifytag.presentation.edittrack.view.track.SpotifyTrackCard

@Composable
fun TrackAndTagsColumn(
    modifier: Modifier = Modifier,
    tags: List<String>,
    onTagClicked: (Int) -> Unit,
    track: TrackViewState?,
    scaleFactor: Float
) {
    Column(
        modifier = modifier
    )
    {
        SpotifyTrackCard(
            modifier = Modifier.fillMaxWidth(fraction = scaleFactor)
                .padding(start = 24.dp, top = 24.dp, end = 24.dp)
                .align(Alignment.CenterHorizontally),
            scaleFactor,
            track = track
        )
        TagsLayout(
            modifier = Modifier.fillMaxSize(),
            tags = tags,
            onTagClicked = onTagClicked
        )
    }
}