package com.adriano.spotifytag.presentation.edittrack.view.fab

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import com.adriano.spotifytag.presentation.edittrack.view.EditModeTransitionDuration
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FabTextField(
    extended: Boolean,
    text: String,
    onTextChange: (String) -> Unit
) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    ToggleKeyboard(focusRequester, extended)

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        singleLine = true,
        shape = RectangleShape,
        textStyle = TextStyle(color = Color.Black),
        value = text, onValueChange = onTextChange,
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ToggleKeyboard(
    focusRequester: FocusRequester,
    extended: Boolean
) {
    LaunchedEffect(extended) {
        if (extended) {
            delay(EditModeTransitionDuration.toLong())
            focusRequester.requestFocus()
        } else {
            focusRequester.freeFocus()
        }
    }
}
