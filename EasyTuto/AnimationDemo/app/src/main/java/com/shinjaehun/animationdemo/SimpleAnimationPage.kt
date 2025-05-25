package com.shinjaehun.animationdemo

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun SimpleAnimationPage(modifier: Modifier = Modifier) {

//    var scale = remember {
//        Animatable(0f)
//    }

    var rotation = remember {
        Animatable(0f)
    }

    var animateAgain by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = animateAgain) {
//        scale.animateTo(
//            targetValue = 1f,
//            animationSpec = tween(
//                durationMillis = 1000,
//                easing = {
//                    OvershootInterpolator(2f).getInterpolation(it)
//                }
//            )
//        )
        rotation.animateTo(
            targetValue = 360f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(200.dp)
                .weight(1f)
//                .scale(scale.value),
                .rotate(rotation.value),
            painter = painterResource(R.drawable.unnamed),
            contentDescription = "logo"
        )
        Button(
            onClick = {
                GlobalScope.launch {
//                    scale.snapTo(0f)
                    rotation.snapTo(0f)
                }
                animateAgain = !animateAgain
            }
        ) {
            Text(text = "Animate")
        }
    }
}