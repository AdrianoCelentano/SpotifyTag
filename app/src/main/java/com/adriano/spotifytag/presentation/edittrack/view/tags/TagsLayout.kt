package com.adriano.spotifytag.presentation.edittrack.view.tags

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TagsLayout(
    modifier: Modifier = Modifier,
    tags: List<String>,
    onTagClicked: (Int) -> Unit
) {
    Box(modifier = modifier) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier) {
                FlowLayout(modifier = Modifier.padding(bottom = 20.dp)) {
                    tags.forEachIndexed { index, text ->
                        Chip(
                            onClick = { onTagClicked(index) },
                            modifier = Modifier.padding(top = 16.dp, start = 16.dp),
                            text = text,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(gradient())
        )
    }
}

@Composable
private fun gradient() = remember {
    Brush.verticalGradient(listOf(Color.White) + List(20) { Color.Transparent })
}
