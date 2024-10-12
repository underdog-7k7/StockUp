package com.personal.animeshpandey.stockup

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

//Somehow the current Jetpack BOM Has some issues with the loading indicator hence a custom 3rd party animation has been used
//Courtesy mention https://github.com/cp-radhika-s
//Animation repo link https://github.com/canopas/compose-animations-examples/blob/main/app/src/main/java/com/canopas/composeanimations/animations/RotateDotAnimation.kt

@SuppressLint("SuspiciousIndentation")
@Composable
fun RotateDotAnimation() {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
        ), label = ""
    )

        Canvas(modifier = Modifier.size(4.dp)) {

            val x = (center.x + cos(Math.toRadians(rotation.toDouble())) * 120f).toFloat()
            val y = (center.y + sin(Math.toRadians(rotation.toDouble())) * 120f).toFloat()

            drawCircle(
                Color.Black, center = Offset(x, y),
                radius = 20f
            )
        }
    }


@Composable
fun RotatingCircle() {

    var xRotation by remember {
        mutableStateOf(0f)
    }
    var yRotation by remember {
        mutableStateOf(0f)
    }

    LaunchedEffect(key1 = Unit, block = {
        while (true) {
            animate(
                0f,
                180f,
                animationSpec = tween(800, easing = LinearOutSlowInEasing),
                block = { value, _ -> xRotation = value }
            )
            animate(
                0f,
                180f,
                animationSpec = tween(600, easing = LinearEasing),
                block = { value, _ -> yRotation = value }
            )
        }
    })

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .graphicsLayer {
                    rotationX = xRotation
                    rotationY = yRotation
                }
                .background(Color.White, CircleShape)
        )
    }
}