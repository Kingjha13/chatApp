package com.example.animationdemo.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.animationdemo.R

@Composable
fun Friend(navController:    NavHostController,authViewModel: AuthViewModel) {
    Column {
        Text(
            text = "Your Connections",
            modifier = Modifier.padding(start = 19.dp, top = 34.dp),
            color = Color.Magenta
        )
        Image(
            painter = painterResource(R.drawable.picon),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 94.dp, top = 32.dp, end = 11.dp)
                .width(190.dp)
                .height(160.dp)
                .border(10.dp, Color(0xFF800080))
                .clickable { navController.navigate("ak") }
        )
        Text(text = "Person 1", modifier = Modifier.padding(start = 160.dp))
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                //navController.navigate("friend")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp), // Adjust button height
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0097A7)),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Listen to Music Together",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                navController.navigate("cal")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor =Color(0xFF8A2BE2)),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Simple Call",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Click to Automatic Call",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                navController.navigate("chatn")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C853)),
            shape = MaterialTheme.shapes.medium
        ) {
            Text(
                text = "Start Chat Now",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
        }

    }
}

