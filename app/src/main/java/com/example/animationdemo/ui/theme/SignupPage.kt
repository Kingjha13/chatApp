package com.example.animationdemo.ui.theme

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.animationdemo.R

@Composable
fun SignupPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is com.example.animationdemo.ui.theme.AuthState.Authenticated ->{
                navController.navigate("main/$name")
            }
            is com.example.animationdemo.ui.theme.AuthState.Error -> Toast.makeText(
                context,
                (authState.value as com.example.animationdemo.ui.theme.AuthState.Error).message, Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }
    Image(painter = painterResource(R.drawable.whatsapp_image_2025_02_28_at_22_03_39_c3b73a3f), contentDescription =null,modifier= Modifier
        .padding(start = 0.dp, end = 0.dp, top = 31.dp)
        .width(740.dp)
        .height(180.dp) )
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome To AS Music \uD83C\uDFB6\uD83C\uDFB5", fontSize = 24.sp, color = Color(0xFF9C27B0))


        OutlinedTextField(
            value =name,
            onValueChange = {
                name = it
            },
            label = { Text(text = "Your Name",color=Color(0xFF0097A7))
            }
        )
        Spacer(modifier=Modifier.height(8.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = { Text(text = "Email",color=Color(0xFF0097A7))
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(text = "Password",color=Color(0xFF0097A7))
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                authViewModel.signup(email, password)
            }, enabled = authState.value != com.example.animationdemo.ui.theme.AuthState.Loading,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text(text = "Create account",color=Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Already have an account?", color = Color.Magenta)

            TextButton(onClick = {
                navController.navigate("login")
            }) {
                Text(text = "Login page")
            }
        }


    }
}