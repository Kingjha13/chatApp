package com.example.animationdemo.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.border
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.animationdemo.R

@Composable
fun Profiles(
    modifier: Modifier, navController: NavHostController,
    authViewModel: AuthViewModel,userName:String
) {
    //var name by remember { mutableStateOf("Supriya") }
    var bio by remember { mutableStateOf("See your bio.") }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .padding(top = 40.dp)
                .size(120.dp)
                .background(Color.Gray, CircleShape)
                .border(3.dp, Color(0xFF0097A7), CircleShape)
                .padding(4.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.whatsapp_image_2024_12_29_at_10_39_31_8e3afc8b),
                contentDescription = "Profile Image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Avanish",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextButton(onClick = {
            navController.navigate("friend")
        }) {
            Text(text = "Connect with Your Special People", color = Color.Blue)
        }
        Text(
            text = bio,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
            color = Color.Magenta
        )

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0097A7))
        ) {
            Text(text = "Edit Profile", color = Color.White)
        }
        TextButton(onClick = {
            authViewModel.signout()
            navController.navigate("login")
        }) {
            Text(text = "Sign out", color = Color.Red)
        }
    }
}
