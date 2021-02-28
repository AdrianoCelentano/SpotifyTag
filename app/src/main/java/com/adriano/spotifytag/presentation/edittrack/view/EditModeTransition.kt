package com.adriano.spotifytag.presentation.edittrack.view

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

enum class EditModeState { Collapsed, Expanded }

class TransitionData(
    cardScaleFactor: State<Float>,
    fabWidthFactor: State<Float>
) {
    val fabWidthFactor by fabWidthFactor
    val cardScaleFactor by cardScaleFactor
}

const val EditModeTransitionDuration = 300

@Composable
fun updateTransitionData(expanded: Boolean): TransitionData {
    val transition =
        updateTransition(if (expanded) EditModeState.Expanded else EditModeState.Collapsed)

    val fabWidthFactor = transition.animateFloat(
        transitionSpec = {
            when {
                EditModeState.Expanded isTransitioningTo EditModeState.Collapsed -> delayedTween()
                else -> defaultTween()
            }
        }
    ) { state ->
        when (state) {
            EditModeState.Collapsed -> 0f
            EditModeState.Expanded -> 1f
        }
    }

    val cardScaleFactor = transition.animateFloat(
        transitionSpec = {
            when {
                EditModeState.Expanded isTransitioningTo EditModeState.Collapsed -> defaultTween()
                else -> delayedTween()
            }
        }
    ) { state ->
        when (state) {
            EditModeState.Collapsed -> 1f
            EditModeState.Expanded -> 0.5f
        }
    }

    return remember(transition) { TransitionData(fabWidthFactor, cardScaleFactor) }
}

private fun defaultTween(): TweenSpec<Float> {
    return tween(
        easing = FastOutSlowInEasing,
        durationMillis = EditModeTransitionDuration,
        delayMillis = 0
    )
}

private fun delayedTween(): TweenSpec<Float> {
    return tween(
        easing = FastOutSlowInEasing,
        durationMillis = EditModeTransitionDuration,
        delayMillis = EditModeTransitionDuration
    )
}
