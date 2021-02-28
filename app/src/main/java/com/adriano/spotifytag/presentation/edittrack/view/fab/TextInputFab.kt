package com.adriano.spotifytag.presentation.edittrack.view.fab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TextInputFab(
    modifier: Modifier = Modifier,
    editMode: Boolean,
    currentTextInput: String,
    onClick: () -> Unit,
    onTextChange: (String) -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .height(TextFieldDefaults.MinHeight)
            .widthIn(min = TextFieldDefaults.MinHeight),
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        FabContent(
            expanded = editMode,
            currentTextInput = currentTextInput,
            onTextChange = onTextChange
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun FabContent(
    expanded: Boolean,
    currentTextInput: String,
    onTextChange: (String) -> Unit
) {
    val iconAsset = if (expanded) Icons.Outlined.Check else Icons.Outlined.Add
    val showText = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = expanded) {
        if (expanded) showText.value = true
        else {
            delay(300) // changing the animationSpec breaks AnimatedVisibility
            showText.value = false
        }
    }

    Row {
        Icon(
            modifier = Modifier
                .fillMaxHeight()
                .width(TextFieldDefaults.MinHeight - 8.dp)
                .padding(8.dp),
            imageVector = iconAsset,
            contentDescription = "Fab",
        )
        AnimatedVisibility(visible = showText.value) {
            FabTextField(
                modifier = Modifier.fillMaxHeight(),
                extended = expanded,
                text = currentTextInput,
                onTextChange = onTextChange
            )
        }
    }
}
