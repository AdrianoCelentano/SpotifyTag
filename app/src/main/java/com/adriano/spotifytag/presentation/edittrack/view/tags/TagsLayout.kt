package com.adriano.spotifytag.presentation.edittrack.view.tags

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TagsLayout(
    modifier: Modifier = Modifier,
    tags: List<String>,
    onTagClicked: (Int) -> Unit
) {
    ScrollableColumn(
        modifier = modifier
    ) {
        FlowLayout(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 24.dp, end = 24.dp),
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
}