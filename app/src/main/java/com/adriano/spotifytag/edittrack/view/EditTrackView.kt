package com.adriano.spotifytag.edittrack.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.viewModel
import com.adriano.spotifytag.edittrack.EditTrackViewModel
import com.adriano.spotifytag.edittrack.TrackViewEvent
import com.adriano.spotifytag.edittrack.view.fab.TextInputFab

@Composable
fun EditTrackView() {

    val editTrackViewModel: EditTrackViewModel = viewModel()

    Surface(color = MaterialTheme.colors.background) {

        Box(modifier = Modifier.fillMaxSize()) {
            TrackAndTagsColumn(
                modifier = Modifier.fillMaxSize(),
                tags = editTrackViewModel.state.tags,
                onTagClicked = { index: Int ->
                    editTrackViewModel.event(TrackViewEvent.TagClicked(index))
                }
            )
            TextInputFab(
                modifier = Modifier.align(Alignment.BottomEnd),
                fabState = editTrackViewModel.state.fabState,
                onTextChange = { input: String ->
                    editTrackViewModel.event(TrackViewEvent.TagTextChanged(input))
                },
                onClick = { editTrackViewModel.event(TrackViewEvent.FabClicked) }
            )
        }
    }
}
