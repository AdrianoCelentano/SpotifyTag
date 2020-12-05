package com.adriano.spotifytag.presentation.edittrack.view.fab

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
import com.adriano.spotifytag.presentation.edittrack.view.EditModeTransitionDuration
import kotlinx.coroutines.delay

@OptIn(ExperimentalFocus::class)
@Composable
fun FabTextField(
    extended: Boolean,
    text: String,
    onTextChange: (String) -> Unit
) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    var keyboardController by remember { mutableStateOf<SoftwareKeyboardController?>(null) }
    toggleKeyboard(focusRequester, keyboardController, extended)

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

@OptIn(ExperimentalFocus::class)
@Composable
private fun toggleKeyboard(
    focusRequester: FocusRequester,
    keyboardController: SoftwareKeyboardController?,
    extended: Boolean
) {
    LaunchedEffect(extended, keyboardController) {
        if (extended) {
            delay(EditModeTransitionDuration.toLong())
            focusRequester.requestFocus()
        }
        keyboardController?.let { controller ->
            if (extended) controller.showSoftwareKeyboard()
            else controller.hideSoftwareKeyboard()
        }
    }
}