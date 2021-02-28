package com.adriano.spotifytag.presentation.edittrack.view.fab

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import kotlinx.coroutines.delay

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FabTextField(
    modifier: Modifier = Modifier,
    extended: Boolean,
    text: String,
    onTextChange: (String) -> Unit
) {
    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    ToggleKeyboard(focusRequester, extended)

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        singleLine = true,
        colors = textFieldColors(backgroundColor = Color.White),
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
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(extended) {
        if (extended) {
            delay(300)
            focusRequester.requestFocus()
            keyboardController?.showSoftwareKeyboard()
        } else {
            focusRequester.freeFocus()
            keyboardController?.hideSoftwareKeyboard()
        }
    }
}
