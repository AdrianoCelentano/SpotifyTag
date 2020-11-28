package com.adriano.spotifytag.presentation.edittrack.view.tags

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.VerticalGradient
import androidx.compose.ui.unit.dp

@Composable
fun TagsLayout(
    modifier: Modifier = Modifier,
    tags: List<String>,
    onTagClicked: (Int) -> Unit
) {
    Box(modifier = modifier) {

        ScrollableColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            FlowLayout(
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp)
                    .padding(top = 20.dp, bottom = 8.dp),
                horizontalSpacing = 12.dp,
                verticalSpacing = 4.dp
            ) {
                tags.forEachIndexed { index, text ->
                    Chip(
                        onClick = { onTagClicked(index) },
                        modifier = Modifier,
                        text = text,
                        color = Color.Black
                    )
                }
            }
        }

        GradientView(
            modifier = Modifier.fillMaxWidth()
                .preferredHeight(20.dp)
        )

    }
}

@Composable
fun GradientView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .verticalGradientBackground(listOf(Color.White) + List(20) { Color.Transparent })
    )
}

fun Modifier.verticalGradientBackground(
    colors: List<Color>
) = drawWithCache {
    val gradient = VerticalGradient(startY = 0.0f, endY = size.width, colors = colors)
    onDrawBehind {
        drawRect(brush = gradient)
    }
}