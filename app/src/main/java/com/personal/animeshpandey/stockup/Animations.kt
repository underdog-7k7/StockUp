package com.personal.animeshpandey.stockup

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.cos
import kotlin.math.sin

//Somehow the current Jetpack BOM Has some issues with the loading indicator hence a custom 3rd party animation has been used
//Courtesy mention https://github.com/cp-radhika-s
//Animation repo link https://github.com/canopas/compose-animations-examples/blob/main/app/src/main/java/com/canopas/composeanimations/animations/RotateDotAnimation.kt

@Composable
fun RotateDotAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
        ), label = ""
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Canvas(modifier = Modifier) {

            drawCircle(
                Color.Blue.copy(.6f), center = center,
                radius = 150f,
                style = Stroke(width = 10f)
            )

            val x = (center.x + cos(Math.toRadians(rotation.toDouble())) * 120f).toFloat()
            val y = (center.y + sin(Math.toRadians(rotation.toDouble())) * 120f).toFloat()

            drawCircle(
                Color.Blue, center = Offset(x, y),
                radius = 20f
            )
        }
    }
}