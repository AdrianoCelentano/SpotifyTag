package com.adriano.spotifytag.presentation.createplaylist

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.adriano.spotifytag.presentation.theme.darkGreen
import com.adriano.spotifytag.presentation.theme.lightGreen
import com.adriano.spotifytag.presentation.theme.typography
import kotlin.random.Random

@OptIn(ExperimentalLayout::class)
@Composable
fun CreatePlaylistView() {

    val createPlaylistViewModel: CreatePlaylistViewModel = viewModel()
    val tags = createPlaylistViewModel.tags.collectAsState(initial = listOf()).value

    Surface(color = MaterialTheme.colors.background) {

        Column(modifier = Modifier.fillMaxSize()) {
            Box(Modifier.weight(1f)) {
                ScrollableColumn(
                    modifier = Modifier
                        .align(Alignment.Center)
                ) {
                    FlowRow {
                        tags.forEach {
                            val color = remember { lerp(lightGreen, darkGreen, Random.nextFloat()) }
                            ColorCheckChip(
                                modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                                text = it.name,
                                color = color
                            )
                        }
                    }
                }
            }

            Button(
                modifier = Modifier.wrapContentHeight()
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                shape = RoundedCornerShape(32.dp),
                onClick = {},
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Create Playlist",
                    style = typography.h6
                )
            }
        }
    }
}