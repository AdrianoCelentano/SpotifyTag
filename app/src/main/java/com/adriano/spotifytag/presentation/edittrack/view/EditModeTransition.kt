package com.adriano.spotifytag.presentation.edittrack.view

import androidx.compose.animation.core.*
import androidx.compose.animation.transition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

val FabWidthFactor = FloatPropKey("FabWidth")
val FabAlignmentFactor = FloatPropKey("FabAlignment")
val CardScaleFactor = FloatPropKey("CardScale")

enum class EditModeStates { Collapsed, Expanded }

@Suppress("RemoveExplicitTypeArguments")
fun editModeTransitionDefinition(duration: Int = 300) = transitionDefinition<EditModeStates> {
    state(EditModeStates.Collapsed) {
        this[FabWidthFactor] = 0f
        this[CardScaleFactor] = 1f
    }
    state(EditModeStates.Expanded) {
        this[FabWidthFactor] = 1f
        this[CardScaleFactor] = 0.5f
    }
    val defaultTween = defaultTween(duration)
    val delayedTween = delayedTween(duration)
    transition(
        fromState = EditModeStates.Expanded,
        toState = EditModeStates.Collapsed
    ) {
        FabWidthFactor using defaultTween
        CardScaleFactor using defaultTween
    }
    transition(
        fromState = EditModeStates.Collapsed,
        toState = EditModeStates.Expanded
    ) {
        FabWidthFactor using defaultTween
        CardScaleFactor using defaultTween
    }
}

private fun defaultTween(duration: Int): TweenSpec<Float> {
    return tween(
        easing = FastOutSlowInEasing,
        durationMillis = duration,
        delayMillis = 0
    )
}

private fun delayedTween(duration: Int): TweenSpec<Float> {
    return tween(
        easing = FastOutSlowInEasing,
        durationMillis = duration,
        delayMillis = duration
    )
}

@Composable
fun getEditModeTransition(
    expanded: Boolean = true
): TransitionState {
    val currentState = if (expanded) EditModeStates.Expanded else EditModeStates.Collapsed
    val transitionDefinition = remember { editModeTransitionDefinition() }
    return transition(
        definition = transitionDefinition,
        toState = currentState
    )
}