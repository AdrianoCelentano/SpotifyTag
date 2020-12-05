package com.adriano.spotifytag.presentation.edittrack.view

import androidx.compose.animation.core.*
import androidx.compose.animation.transition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

val FabWidthFactor = FloatPropKey("FabWidth")
val CardScaleFactor = FloatPropKey("CardScale")

enum class EditModeStates { Collapsed, Expanded }

@Suppress("RemoveExplicitTypeArguments")
fun editModeTransitionDefinition() = transitionDefinition<EditModeStates> {

    state(EditModeStates.Collapsed) {
        this[FabWidthFactor] = 0f
        this[CardScaleFactor] = 1f
    }
    state(EditModeStates.Expanded) {
        this[FabWidthFactor] = 1f
        this[CardScaleFactor] = 0.5f
    }
    val defaultTween = defaultTween(EditModeTransitionDuration)
    val delayedTween = delayedTween(EditModeTransitionDuration)
    transition(
        fromState = EditModeStates.Expanded,
        toState = EditModeStates.Collapsed
    ) {
        FabWidthFactor using delayedTween
        CardScaleFactor using defaultTween
    }
    transition(
        fromState = EditModeStates.Collapsed,
        toState = EditModeStates.Expanded
    ) {
        FabWidthFactor using defaultTween
        CardScaleFactor using delayedTween
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

const val EditModeTransitionDuration = 300