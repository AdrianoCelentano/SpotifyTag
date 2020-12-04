package com.adriano.spotifytag.presentation.createplaylist.view

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.adriano.spotifytag.presentation.createplaylist.CreatePlayListViewEvent
import com.adriano.spotifytag.presentation.createplaylist.CreatePlaylistViewModel
import com.adriano.spotifytag.presentation.createplaylist.TagViewState
import com.adriano.spotifytag.presentation.theme.darkGreen
import com.adriano.spotifytag.presentation.theme.lightGreen
import com.adriano.spotifytag.presentation.theme.typography
import dev.chrisbanes.accompanist.insets.systemBarsPadding
import kotlin.random.Random

@OptIn(ExperimentalLayout::class)
@Composable
fun CreatePlaylistView() {

    val createPlaylistViewModel: CreatePlaylistViewModel = viewModel()

    Surface(color = MaterialTheme.colors.background) {

        Column(
            modifier = Modifier.fillMaxSize()
                .systemBarsPadding()
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

            createPlaylistButton(
                createPlaylistClicked = {
                    createPlaylistViewModel.event(CreatePlayListViewEvent.CreatePlaylistClicked)
                }
            )

        }
    }
}

@Composable
private fun createPlaylistButton(
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

@OptIn(ExperimentalLayout::class)
@Composable
private fun TagsLayout(
    modifier: Modifier,
    tags: List<TagViewState>,
    tagClicked: (index: Int) -> Unit
) {
    ScrollableColumn(
        modifier = modifier
    ) {
        FlowRow {
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