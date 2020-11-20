package com.adriano.spotifytag.edittrack

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FloatPropKey
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import androidx.compose.animation.transition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.ExperimentalFocus
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.VectorAsset
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.adriano.spotifytag.util.lerp
import kotlin.math.roundToInt


@Composable
fun AddTextFab(modifier: Modifier = Modifier) {
    val editTrackViewModel = viewModel<EditTrackViewmodel>()
    FloatingActionButton(
        onClick = {},
        modifier = modifier
            .padding(16.dp)
            .preferredHeight(48.dp)
            .widthIn(min = 48.dp),
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary
    ) {
        AnimatingFabContent(
            icon = Icons.Outlined.Add,
            iconExpanded = Icons.Outlined.Check,
            extended = editTrackViewModel.state.fabExpanded
        )
    }
}

@Composable
fun AnimatingFabContent(
    icon: VectorAsset,
    iconExpanded: VectorAsset,
    modifier: Modifier = Modifier,
    extended: Boolean = true
) {
    val currentState = if (extended) ExpandableFabStates.Extended else ExpandableFabStates.Collapsed
    val transitionDefinition = remember { fabTransitionDefinition() }
    val transition = transition(
        definition = transitionDefinition,
        toState = currentState
    )
    IconAndTextRow(
        if (extended) iconExpanded else icon,
        { transition[FabWidthFactor] },
        modifier = modifier
    )
}

@OptIn(ExperimentalFocus::class)
@Composable
private fun IconAndTextRow(
    icon: VectorAsset,
    widthProgress: () -> Float,
    modifier: Modifier
) {
    val editTrackViewModel = viewModel<EditTrackViewmodel>()

    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    if (editTrackViewModel.state.fabExpanded) focusRequester.requestFocus()

    var keyboardController by remember { mutableStateOf<SoftwareKeyboardController?>(null) }
    onCommit(keyboardController, editTrackViewModel.state.fabExpanded) {
        keyboardController?.let { controller ->
            if (editTrackViewModel.state.fabExpanded) controller.showSoftwareKeyboard()
            else controller.hideSoftwareKeyboard()
        }
    }

    Layout(
        modifier = modifier,
        children = {
            Icon(
                modifier = Modifier.clickable {
                    editTrackViewModel.event(TrackViewEvent.FabClicked)
                },
                asset = icon
            )
            TextField(
                modifier = Modifier.fillMaxWidth()
                    .focusRequester(focusRequester),
                maxLines = 1,
                backgroundColor = Color.White,
                shape = RectangleShape,
                textStyle = TextStyle(color = Color.Black),
                value = editTrackViewModel.state.newTagText, onValueChange = { input: String ->
                    editTrackViewModel.event(TrackViewEvent.TagTextChanged(input))
                },
                onTextInputStarted = { controller -> keyboardController = controller }
            )
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
        val textPlaceable = measurables[1].measure(textInputConstraints)

        layout(width.roundToInt(), height) {
            iconPlaceable.place(
                iconPadding.roundToInt(),
                constraints.maxHeight / 2 - iconPlaceable.height / 2
            )
            textPlaceable.place(
                (iconPlaceable.width + iconPadding * 2).roundToInt(),
                constraints.maxHeight / 2 - textPlaceable.height / 2
            )
        }
    }
}

private val FabWidthFactor = FloatPropKey("Width")

private enum class ExpandableFabStates { Collapsed, Extended }

@Suppress("RemoveExplicitTypeArguments")
private fun fabTransitionDefinition(duration: Int = 200) =
    transitionDefinition<ExpandableFabStates> {
        state(ExpandableFabStates.Collapsed) {
            this[FabWidthFactor] = 0f
        }
        state(ExpandableFabStates.Extended) {
            this[FabWidthFactor] = 1f
        }
        transition(
            fromState = ExpandableFabStates.Extended,
            toState = ExpandableFabStates.Collapsed
        ) {
            FabWidthFactor using tween<Float>(
                easing = FastOutSlowInEasing,
                durationMillis = duration
            )
        }
        transition(
            fromState = ExpandableFabStates.Collapsed,
            toState = ExpandableFabStates.Extended
        ) {
            FabWidthFactor using tween<Float>(
                easing = FastOutSlowInEasing,
                durationMillis = duration
            )
        }
    }

