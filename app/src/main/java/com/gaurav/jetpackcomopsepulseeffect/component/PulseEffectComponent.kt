package com.gaurav.jetpackcomopsepulseeffect.component

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.scale


@Composable
fun Modifier.doubleApplyPulseEffect(
    targetScale: Float = 1.5f,
    initialScale: Float = 1.0f,
    brush: Brush = SolidColor(Color.Black.copy(0.32f)),
    shape: Shape = CircleShape,
    durationMillis: Int = 1200

): Modifier {
    return this
        .applyPulseEffect(
            targetScale = targetScale,
            initialScale = initialScale,
            brush = brush,
            shape = shape,
            animationSpec = tween(durationMillis = durationMillis, easing = FastOutSlowInEasing)
        )
        .applyPulseEffect(
            targetScale = targetScale,
            initialScale = initialScale,
            brush = brush,
            shape = shape,
            animationSpec = tween(
                durationMillis = (durationMillis * 0.7f).toInt(),
                delayMillis = (durationMillis * 0.3f).toInt(),
                easing = LinearEasing
            )
        )
}

@Composable
fun Modifier.applyPulseEffect(
    targetScale: Float = 1.5f,
    initialScale: Float = 1.0f,
    brush: Brush = SolidColor(Color.Black.copy(0.32f)),
    shape: Shape = CircleShape,
    animationSpec: DurationBasedAnimationSpec<Float> = tween(durationMillis = 1200)
): Modifier {

    val pulseTransition = rememberInfiniteTransition("Pulse Transition")
    val pulseScale by pulseTransition.animateFloat(
        initialValue = initialScale,
        targetValue = targetScale,
        animationSpec = infiniteRepeatable(animationSpec),
        label = "Pulse Animation"
    )

    val pulseAlpha by pulseTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(animationSpec),
        label = "Pulse Alpha"
    )

    return this.drawBehind {
        val outline = shape.createOutline(size, layoutDirection, this)
        scale(pulseScale) {
            drawOutline(outline, brush, pulseAlpha)
        }
    }
}