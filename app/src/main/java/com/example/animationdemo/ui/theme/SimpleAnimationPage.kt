package com.example.animationdemo.ui.theme

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.animationdemo.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SimpleAnimationPage(modifier: Modifier = Modifier, navController: NavHostController) {

    val scale = remember {
        Animatable(2f)
    }

    var animateAgain by remember {
        mutableStateOf(false)
    }

    // Click counter
    var clickCount by remember { mutableStateOf(0) }
    var showMessage by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = animateAgain) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 2000,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
    }

    // Increment click counter when button is pressed
    val onButtonClick = {
        clickCount++
        if (clickCount >= 10) {
            showMessage = true
        } else {
            showMessage = false

            // Show "No" for 1 second when click count is less than 10
            GlobalScope.launch {
                showMessage = true // Show the "No" message
                delay(1000) // Wait for 1 second
                showMessage = false // Hide the "No" message after 1 second
            }
        }

        GlobalScope.launch {
            scale.snapTo(2f) // Reset scale before starting the animation
        }
        animateAgain = !animateAgain
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = R.drawable.whatsapp_image_2025_03_09_at_20_37_29_00356795), contentDescription = null, modifier = Modifier.width(200.dp).padding(top=8.dp))
        Box(
            modifier = Modifier
                .padding(11.dp)
                .padding(start = 1.dp)
                .background(Color(0xFF4B0082))
        ) {
            Text(
                text = "Please forgive me, Motu. I love you deeply, and I'm truly sorry for my anger. You are my queen. Love you meri jhumka queen ❤❤",
                color = Color.White
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .border(
                    width = 8.dp,
                    color = Color(0xFF800080),
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(RoundedCornerShape(16.dp))
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Green,
                            Color.Blue
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
                .clickable { navController.navigate("signup") }
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Re", color = Color.White)
        }
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            // Change the image based on click count
            val imageResource = if (clickCount >= 10) {
                R.drawable.motu_pic
            } else {
                R.drawable.whatsapp_image_2025_03_09_at_17_44_59_5ae49117 // Default image
            }

            Image(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.Center)
                    .scale(scale.value)
                    .width(400.dp)
                    .height(270.dp),
                painter = painterResource(id = imageResource),
                contentDescription = "logo"
            )

            // Show message inside the image when the counter reaches 10
            if (clickCount >= 10) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(Color(0xFF003202)) // Semi-transparent background
                        .padding(10.dp)
                ) {
                    Text(
                        text = "Ok me apne ladaku viman pe etna gussa kaese ho sakti hu\uD83E\uDD70\uD83D\uDE18\uD83D\uDE0ALove you my dear husband.",
                        color = Color.Magenta,modifier=Modifier.background( Color(0xFFDAA520))
                    )
                }
            } else if (showMessage) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .background(Color(0x80000000))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "No",
                        color = Color(0xFFDAA520)
                    )
                }
            }
        }

        Button(
            onClick = onButtonClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF003366), // Set the background color
                contentColor = Color.White // Set the text color to white
            )
        ) {
            Text(text = "Please motu maf kar do", color = Color.White)
        }
    }
}