package com.adriano.spotifytag.edittrack.view.fab

import androidx.compose.animation.core.*
import androidx.compose.animation.transition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

val FabWidthFactor = FloatPropKey("Width")

enum class ExpandableFabStates { Collapsed, Expanded }

@Suppress("RemoveExplicitTypeArguments")
fun fabTransitionDefinition(duration: Int = 200) =
    transitionDefinition<ExpandableFabStates> {
        state(ExpandableFabStates.Collapsed) {
            this[FabWidthFactor] = 0f
        }
        state(ExpandableFabStates.Expanded) {
            this[FabWidthFactor] = 1f
        }
        transition(
            fromState = ExpandableFabStates.Expanded,
            toState = ExpandableFabStates.Collapsed
        ) {
            FabWidthFactor using tween<Float>(
                easing = FastOutSlowInEasing,
                durationMillis = duration
            )
        }
        transition(
            fromState = ExpandableFabStates.Collapsed,
            toState = ExpandableFabStates.Expanded
        ) {
            FabWidthFactor using tween<Float>(
                easing = FastOutSlowInEasing,
                durationMillis = duration
            )
        }
    }

@Composable
fun getFabTransition(
    expanded: Boolean = true
): TransitionState {
    val currentState = if (expanded) ExpandableFabStates.Expanded else ExpandableFabStates.Collapsed
    val transitionDefinition = remember { fabTransitionDefinition() }
    return transition(
        definition = transitionDefinition,
        toState = currentState
    )
}