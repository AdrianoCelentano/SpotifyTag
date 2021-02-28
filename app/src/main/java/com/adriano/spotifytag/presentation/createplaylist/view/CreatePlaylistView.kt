package com.adriano.spotifytag.presentation.createplaylist.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp
import com.adriano.spotifytag.presentation.createplaylist.CreatePlayListViewEvent
import com.adriano.spotifytag.presentation.createplaylist.CreatePlaylistViewModel
import com.adriano.spotifytag.presentation.createplaylist.TagViewState
import com.adriano.spotifytag.presentation.edittrack.view.tags.FlowLayout
import com.adriano.spotifytag.presentation.theme.darkGreen
import com.adriano.spotifytag.presentation.theme.lightGreen
import com.adriano.spotifytag.presentation.theme.typography
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import kotlin.random.Random

@Composable
fun CreatePlaylistView(
    innerPadding: PaddingValues,
    createPlaylistViewModel: CreatePlaylistViewModel
) {

    Surface(color = MaterialTheme.colors.background) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(innerPadding)
        ) {

            TagsLayout(
                modifier = Modifier.weight(1f),
                tags = createPlaylistViewModel.state.tags,
                tagClicked = { tagIndex ->
                    createPlaylistViewModel.event(
                        CreatePlayListViewEvent.TagClicked(
                            tagIndex
                        )
                    )
                }
            )

            CreatePlaylistButton(
                createPlaylistClicked = {
                    createPlaylistViewModel.event(CreatePlayListViewEvent.CreatePlaylistClicked)
                }
            )
        }
    }
}

@Composable
private fun CreatePlaylistButton(
    createPlaylistClicked: () -> Unit
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        shape = RoundedCornerShape(24.dp),
        onClick = createPlaylistClicked,
        contentPadding = PaddingValues(12.dp)
    ) {
        Text(
            text = "Create Playlist",
            style = typography.h6
        )
    }
}

@Composable
private fun TagsLayout(
    modifier: Modifier,
    tags: List<TagViewState>,
    tagClicked: (index: Int) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        FlowLayout {
            tags.forEachIndexed { index, tag ->
                val color = remember { lerp(lightGreen, darkGreen, Random.nextFloat()) }
                ColorCheckChip(
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                    text = tag.name,
                    color = color,
                    checked = tag.checked,
                    setChecked = { tagClicked(index) }
                )
            }
        }
    }
}
