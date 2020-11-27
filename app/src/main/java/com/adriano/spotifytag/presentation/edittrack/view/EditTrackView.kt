package com.adriano.spotifytag.presentation.edittrack.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.viewModel
import com.adriano.spotifytag.presentation.edittrack.EditTrackViewModel
import com.adriano.spotifytag.presentation.edittrack.TrackViewEvent
import com.adriano.spotifytag.presentation.edittrack.view.fab.TextInputFab

@Composable
fun EditTrackView() {

    val editTrackViewModel: EditTrackViewModel = viewModel()

    Surface(color = MaterialTheme.colors.background) {

        Box(modifier = Modifier.fillMaxSize()) {

            val transition = getEditModeTransition(editTrackViewModel.state.editMode)

            TrackAndTagsColumn(
                modifier = Modifier.fillMaxSize(),
                tags = editTrackViewModel.state.tags,
                scaleFactor = transition[CardScaleFactor],
                onTagClicked = { index: Int ->
                    editTrackViewModel.event(TrackViewEvent.TagClicked(index))
                },
                track = editTrackViewModel.state.currentTrack
            )

            val alignment = BiasAlignment(1f, transition[FabAlignmentFactor])
            TextInputFab(
                modifier = Modifier.align(alignment),
                editMode = editTrackViewModel.state.editMode,
                currentTextInput = editTrackViewModel.state.currentTextInput,
                onTextChange = { input: String ->
                    editTrackViewModel.event(TrackViewEvent.TagTextChanged(input))
                },
                onClick = { editTrackViewModel.event(TrackViewEvent.FabClicked) }
            )
        }
    }
}
