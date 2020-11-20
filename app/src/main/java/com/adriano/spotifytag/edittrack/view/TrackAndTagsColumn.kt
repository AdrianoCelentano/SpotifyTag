package com.adriano.spotifytag.edittrack.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.adriano.spotifytag.edittrack.view.tags.TagsLayout
import com.adriano.spotifytag.edittrack.view.track.SpotifyTrackCard

@Composable
fun TrackAndTagsColumn(
    modifier: Modifier = Modifier,
    tags: List<String>,
    onTagClicked: (Int) -> Unit
) {
    Column(
        modifier = modifier
    )
    {
        SpotifyTrackCard(
            modifier = Modifier.fillMaxWidth()
                .padding(24.dp),
        )
        TagsLayout(
            modifier = Modifier.fillMaxSize()
                .padding(bottom = 80.dp),
            tags = tags,
            onTagClicked = onTagClicked
        )
    }
}