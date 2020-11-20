package com.adriano.spotifytag.edittrack

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.adriano.spotifytag.shared.Chip
import com.adriano.spotifytag.shared.FlowLayout

@Composable
fun EditTrackView() {

    val editTrackViewmodel: EditTrackViewmodel = viewModel()

    Surface(color = MaterialTheme.colors.background) {

        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier.fillMaxSize()
            )
            {
                SpotifyTrackState(
                    modifier = Modifier.fillMaxWidth()
                        .padding(24.dp),
                )

                ScrollableColumn(
                    modifier = Modifier.fillMaxSize()
                        .padding(bottom = 80.dp)
                ) {
                    FlowLayout(
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 24.dp, end = 24.dp),
                        horizontalSpacing = 12.dp,
                        verticalSpacing = 4.dp
                    ) {
                        editTrackViewmodel.state.tags.forEachIndexed { index, text ->
                            Chip(
                                onClick = {
                                    editTrackViewmodel.event(TrackViewEvent.TagClicked(index))
                                },
                                modifier = Modifier,
                                text = text
                            )
                        }
                    }
                }
            }

            AddTextFab(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
            )
        }
    }
}
