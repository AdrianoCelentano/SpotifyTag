package com.adriano.spotifytag.presentation.edittrack.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adriano.spotifytag.presentation.edittrack.EditTrackViewEvent
import com.adriano.spotifytag.presentation.edittrack.EditTrackViewModel
import com.adriano.spotifytag.presentation.edittrack.view.fab.TextInputFab
import dev.chrisbanes.accompanist.insets.AmbientWindowInsets
import dev.chrisbanes.accompanist.insets.imePadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@Composable
fun EditTrackView(
    innerPadding: PaddingValues,
    editTrackViewModel: EditTrackViewModel
) {

    Surface(color = MaterialTheme.colors.background) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            val transition = getEditModeTransition(editTrackViewModel.state.editMode)

            TrackAndTagsColumn(
                modifier = Modifier.fillMaxSize()
                    .statusBarsPadding()
                    .padding(innerPadding),
                tags = editTrackViewModel.state.tags,
                scaleFactor = transition[CardScaleFactor],
                onTagClicked = { index: Int ->
                    editTrackViewModel.event(EditTrackViewEvent.TagClicked(index))
                },
                track = editTrackViewModel.state.currentTrack
            )

            TextInputFab(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = optionalBottomNavPadding(innerPadding))
                    .imePadding()
                    .padding(16.dp),
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

@Composable
private fun optionalBottomNavPadding(
    innerPadding: PaddingValues
): Dp {
    return if (AmbientWindowInsets.current.ime.bottom.dp > innerPadding.bottom) 0.dp else innerPadding.bottom
}