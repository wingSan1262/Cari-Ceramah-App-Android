package com.example.homescreen.compose_ui

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.Alignment


fun DrawerFadeIn(): EnterTransition {
    return fadeIn(
        animationSpec = tween(durationMillis = 300)
    ) + expandHorizontally(
        expandFrom = Alignment.End,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutLinearInEasing,
        )
    )
}

fun DrawerFadeInBottomVertical(): EnterTransition {
    return slideInVertically(
        initialOffsetY = { 2500 }, // small slide 300px
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearEasing // interpolator
        )
    )
}

fun DrawerFadeOutBottomVertical(): ExitTransition {
    return fadeOut(
        animationSpec = tween(
            durationMillis = 700,
            easing = FastOutLinearInEasing,
        )
    ) + shrinkVertically(
        shrinkTowards = Alignment.Bottom,
        animationSpec = tween(
            durationMillis = 700,
            easing = FastOutLinearInEasing,
        )
    )
}

fun DrawerFadeOut(): ExitTransition {
   return fadeOut(
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutLinearInEasing,
        )
    ) + shrinkHorizontally(
        shrinkTowards = Alignment.End,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutLinearInEasing,
        )
    )
}