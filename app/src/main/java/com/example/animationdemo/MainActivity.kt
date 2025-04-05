package com.example.animationdemo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.animationdemo.ui.theme.AnimationDemoTheme
import com.example.animationdemo.ui.theme.AuthViewModel
import com.example.animationdemo.ui.theme.Bhojpuri
import com.example.animationdemo.ui.theme.Call
import com.example.animationdemo.ui.theme.CallPhoneScreen
import com.example.animationdemo.ui.theme.ChatMain
import com.example.animationdemo.ui.theme.ChatScreen
import com.example.animationdemo.ui.theme.English
import com.example.animationdemo.ui.theme.Friend
import com.example.animationdemo.ui.theme.Hindi
import com.example.animationdemo.ui.theme.LoginPage
import com.example.animationdemo.ui.theme.MainScreen
import com.example.animationdemo.ui.theme.Maithili
import com.example.animationdemo.ui.theme.Profiles
import com.example.animationdemo.ui.theme.Punjabi
import com.example.animationdemo.ui.theme.Rap
import com.example.animationdemo.ui.theme.SignupPage

import com.example.animationdemo.NavigationSetup as NavigationSetup1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel : AuthViewModel by viewModels()
        setContent {
            AnimationDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationSetup1(Modifier.padding(innerPadding),authViewModel = authViewModel)
                }
            }
        }
    }
}
@Composable
fun NavigationSetup(modifier: Modifier = Modifier,authViewModel:AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
//        composable("cha"){
//            ChatScreen(
//                modifier, navController, authViewModel = authViewModel,
//                userName = "Avanish"
//            )
//        }
        composable("chatn"){
            ChatMain(modifier,navController,authViewModel=authViewModel)
        }

        composable("friend") {
            Friend(
                navController,authViewModel=authViewModel
            )
        }
        composable("login") {
            LoginPage(
                modifier, navController,
                authViewModel = authViewModel
            )
        }
//        composable(
//            "chat_scree/{userName}",
//            arguments = listOf(navArgument("userName") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val userName = backStackEntry.arguments?.getString("userName") ?: "Anonymous"
//            // Pass the userName to ChatActivity or equivalent composable
//            Profiles(modifier, navController,
//                authViewModel = authViewModel,userName=userName
//            )
//        }
        composable(
            "chat_scree/{userName}",
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: "Anonymous"
            // Pass the userName to ChatActivity or equivalent composable
            Profiles(modifier, navController,
                authViewModel = authViewModel,userName=userName
            )
        }
        composable(
                "chat_screen/{userName}",
        arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
        val userName = backStackEntry.arguments?.getString("userName") ?: "Anonymous"
        // Pass the userName to ChatActivity or equivalent composable
        ChatScreen(modifier, navController,
            authViewModel = authViewModel,userName=userName
        )
    }

        composable(
            "main/{userName}",
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: "Anonymous"
            // Pass the userName to ChatActivity or equivalent composable
            MainScreen(modifier, navController,
                authViewModel = authViewModel,userName=userName
            )
        }
        composable("sign") {
            SignupPage(modifier,navController,authViewModel=authViewModel)
        }
        composable("ak"){
            ReelScreen(modifier,navController)
        }
        composable("hs"){
            Hindi(modifier,navController)
        }
        composable("video/{videoUrl}") { backStackEntry ->
            val videoUrl = backStackEntry.arguments?.getString("videoUrl") ?: ""
            Videos(videoUrl = videoUrl, modifier, navController)
        }
        composable("english") {
            English(modifier,navController)
        }
        composable("punjabi"){
            Punjabi(modifier,navController)
        }
        composable("bhojpuri"){
            Bhojpuri(modifier,navController)
        }
        composable("marathi"){
            Maithili(modifier,navController)
        }
//        composable("profile"){
//            Profiles(
//                modifier, navController,
//                authViewModel = authViewModel
//            )
//        }
        composable("rap") {
            Rap(modifier,navController,authViewModel=authViewModel)
        }
//        composable("friend") {
//            Friend(
//                navController
//            )
//        }
        composable("cal") {
            CallPhoneScreen(modifier,navController,authViewModel=authViewModel)
        }
        composable("call") {
            Call()
        }
    }
}

