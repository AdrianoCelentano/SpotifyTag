package com.adriano.spotifytag.edittrack.view.fab

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle

@OptIn(ExperimentalFocus::class)
@Composable
fun FabTextField(
    extended: Boolean,
    text: String,
    onTextChange: (String) -> Unit
) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    if (extended) focusRequester.requestFocus()

    var keyboardController by remember { mutableStateOf<SoftwareKeyboardController?>(null) }
    toggleKeyboard(keyboardController, extended)

    TextField(
        modifier = Modifier.fillMaxWidth()
            .focusRequester(focusRequester),
        maxLines = 1,
        backgroundColor = Color.White,
        shape = RectangleShape,
        textStyle = TextStyle(color = Color.Black),
        value = text, onValueChange = onTextChange,
        onTextInputStarted = { controller -> keyboardController = controller }
    )
}

@Composable
private fun toggleKeyboard(
    keyboardController: SoftwareKeyboardController?,
    extended: Boolean
) {
    onCommit(keyboardController, extended) {
        keyboardController?.let { controller ->
            if (extended) controller.showSoftwareKeyboard()
            else controller.hideSoftwareKeyboard()
        }
    }
}