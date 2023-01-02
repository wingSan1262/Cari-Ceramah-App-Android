package com.example.basecomponent.base_compose_component

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun ShimmerAnimation(
    outerPadding : Dp = 16.dp,
    modifier: Modifier = Modifier.fillMaxWidth()
        .size(250.dp)
) {

    val ShimmerColorShades = listOf(
        Color.LightGray.copy(0.6f),
        Color.LightGray.copy(0.2f),
        Color.LightGray.copy(0.6f)
    )

    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = ShimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )
    ShimmerItem(brush = brush, outerPadding,modifier = modifier)
}

@Composable
fun ShimmerItem(
    brush: Brush,
    outerPadding : Dp = 16.dp,
    modifier: Modifier = Modifier.fillMaxWidth()
        .size(250.dp)
) {
    Column(modifier = Modifier.padding(outerPadding)) {
        Spacer(
            modifier = modifier
                .background(brush = brush)
        )
    }
}