package com.adriano.spotifytag.presentation.edittrack.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.viewModel
import com.adriano.spotifytag.presentation.edittrack.EditTrackViewEvent
import com.adriano.spotifytag.presentation.edittrack.EditTrackViewModel
import com.adriano.spotifytag.presentation.edittrack.view.fab.TextInputFab
import dev.chrisbanes.accompanist.insets.navigationBarsWithImePadding

@Composable
fun EditTrackView() {

    val editTrackViewModel: EditTrackViewModel = viewModel()

    Surface(color = MaterialTheme.colors.background) {

        Box(
            modifier = Modifier.fillMaxSize()
                .navigationBarsWithImePadding()
        ) {

            val transition = getEditModeTransition(editTrackViewModel.state.editMode)

            TrackAndTagsColumn(
                modifier = Modifier.fillMaxSize(),
                tags = editTrackViewModel.state.tags,
                scaleFactor = transition[CardScaleFactor],
                onTagClicked = { index: Int ->
                    editTrackViewModel.event(EditTrackViewEvent.TagClicked(index))
                },
                track = editTrackViewModel.state.currentTrack
            )

            TextInputFab(
                modifier = Modifier
                    .align(Alignment.BottomEnd),
                editMode = editTrackViewModel.state.editMode,
                currentTextInput = editTrackViewModel.state.currentTextInput,
                onTextChange = { input: String ->
                    editTrackViewModel.event(EditTrackViewEvent.TagTextChanged(input))
                },
                onClick = { editTrackViewModel.event(EditTrackViewEvent.FabClicked) }
            )
        }
    }
}
