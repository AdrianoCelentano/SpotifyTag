package com.adriano.spotifytag.presentation.edittrack.view.fab

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.adriano.spotifytag.presentation.edittrack.view.FabWidthFactor
import com.adriano.spotifytag.presentation.edittrack.view.getEditModeTransition
import com.adriano.spotifytag.presentation.util.lerp
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import kotlin.math.roundToInt


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
            .padding(16.dp)
            .navigationBarsPadding()
            .preferredHeight(48.dp)
            .widthIn(min = 48.dp),
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

@Composable
private fun FabContent(
    expanded: Boolean,
    currentTextInput: String,
    onTextChange: (String) -> Unit
) {
    val transition = getEditModeTransition(expanded = expanded)
    val iconAsset = if (expanded) Icons.Outlined.Check else Icons.Outlined.Add
    IconAndTextFieldRow(
        widthProgress = { transition[FabWidthFactor] },
        icon = { Icon(iconAsset) },
        textField = {
            FabTextField(
                extended = expanded,
                text = currentTextInput,
                onTextChange = onTextChange
            )
        }
    )
}

@OptIn(ExperimentalFocus::class)
@Composable
private fun IconAndTextFieldRow(
    modifier: Modifier = Modifier,
    widthProgress: () -> Float,
    icon: @Composable () -> Unit,
    textField: @Composable () -> Unit,
) {
    Layout(
        modifier = modifier,
        content = {
            icon()
            textField()
        }
    ) { measurables, constraints ->

        val height = constraints.maxHeight
        val initialWidth = height.toFloat()
        val expandedWidth = constraints.maxWidth.toFloat()
        val width = lerp(initialWidth, expandedWidth, widthProgress())

        val iconPlaceable = measurables[0].measure(constraints)
        val iconPadding = (initialWidth - iconPlaceable.width) / 2f

        val textInputConstraints = Constraints(
            minWidth = constraints.minWidth,
            minHeight = constraints.minWidth,
            maxHeight = constraints.maxHeight,
            maxWidth = (constraints.maxWidth - (iconPlaceable.width) + 2 * iconPadding.toInt()),
        )
        val textFieldPlaceable = measurables[1].measure(textInputConstraints)

        layout(width.roundToInt(), height) {
            iconPlaceable.place(
                x = iconPadding.roundToInt(),
                y = constraints.maxHeight / 2 - iconPlaceable.height / 2
            )
            textFieldPlaceable.place(
                x = (iconPlaceable.width + iconPadding * 2).roundToInt(),
                y = constraints.maxHeight / 2 - textFieldPlaceable.height / 2
            )
        }
    }
}

