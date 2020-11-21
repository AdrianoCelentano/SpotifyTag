package com.adriano.spotifytag.edittrack.view

import androidx.compose.animation.core.*
import androidx.compose.animation.transition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

val FabWidthFactor = FloatPropKey("FabWidth")
val FabAlignmentFactor = FloatPropKey("FabAlignment")
val CardScaleFactor = FloatPropKey("CardScale")

enum class EditModeStates { Collapsed, Expanded }

@Suppress("RemoveExplicitTypeArguments")
fun editModeTransitionDefinition(duration: Int = 300) =
    transitionDefinition<EditModeStates> {
        state(EditModeStates.Collapsed) {
            this[FabWidthFactor] = 0f
            this[FabAlignmentFactor] = 1f
            this[CardScaleFactor] = 1f
        }
        state(EditModeStates.Expanded) {
            this[FabWidthFactor] = 1f
            this[FabAlignmentFactor] = 0.2f
            this[CardScaleFactor] = 0.5f
        }
        val delayedTween = tween<Float>(
            easing = FastOutSlowInEasing,
            durationMillis = duration,
            delayMillis = duration
        )
        val tween = tween<Float>(
            easing = FastOutSlowInEasing,
            durationMillis = duration,
            delayMillis = 0
        )
        transition(
            fromState = EditModeStates.Expanded,
            toState = EditModeStates.Collapsed
        ) {
            FabWidthFactor using tween
            FabAlignmentFactor using delayedTween
            CardScaleFactor using delayedTween
        }
        transition(
            fromState = EditModeStates.Collapsed,
            toState = EditModeStates.Expanded
        ) {
            FabWidthFactor using delayedTween
            FabAlignmentFactor using tween
            CardScaleFactor using tween
        }
    }

@Composable
fun getEditModeTransition(
    expanded: Boolean = true
): TransitionState {
    val currentState = if (expanded) EditModeStates.Expanded else EditModeStates.Collapsed
    val transitionDefinition = remember(currentState) { editModeTransitionDefinition() }
    return transition(
        definition = transitionDefinition,
        toState = currentState
    )
}