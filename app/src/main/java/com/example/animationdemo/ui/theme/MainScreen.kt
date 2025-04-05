package com.example.animationdemo.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.animationdemo.R

@Composable
fun MainScreen(
    modifier: Modifier, navController: NavHostController,
    userName: String,
    authViewModel: AuthViewModel
) {
    var text by remember { mutableStateOf("") }
    val authViewModel:AuthViewModel= viewModel()
    //Row {
//        TextButton(onClick = {
//            authViewModel.signout()
//        }) {
//            Text(text ="sign out")
//        }
//        TextField(
//            value = text,
//            onValueChange = { text = it },
//            placeholder = {
//                Text(
//                    text = "Find your next favorite song",
//                    color = Color(0xFF6A5ACD),
//                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
//                )
//            },
//            modifier = Modifier
//                .padding(top = 3.dp)
//                .padding(horizontal = 16.dp, vertical = 12.dp)
//                .fillMaxWidth()
//                .background(
//                    if (text.isEmpty()) Color.Magenta else Color(0xFFF5F5F5),
//                    shape = RoundedCornerShape(12.dp)
//                ),
//            textStyle = TextStyle(fontSize = 16.sp, color = Color.Black)
//        )

    //}
    //Spacer(modifier=Modifier.height(13.dp))
    Column(){
        Row(){
            Image(
                painter = painterResource(R.drawable.mathili_pic),
                contentDescription = null,
                modifier = Modifier
                    .padding( start = 7.dp, top = 34.dp)
                    .width(175.dp)
                    .height(200.dp)
                    .border(10.dp, Color(0xFF009688))
                    .clickable { navController.navigate("marathi") }
            )
            Image(
                painter = painterResource(R.drawable.hindi_song),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 10.dp, top = 34.dp, end = 10.dp)
                    .width(180.dp)
                    .height(200.dp)
                    .border(10.dp, Color(0xFF3F51B5))
                    .clickable { navController.navigate("hs") }
            )
        }
        Row(){
            Image(
                painter = painterResource(R.drawable.punn),
                contentDescription = null,
                modifier = Modifier
                    .padding( start = 7.dp, top = 10.dp)
                    .width(175.dp)
                    .height(200.dp)
                    .border(10.dp, Color(0xFF009688))
                    .clickable { navController.navigate("punjabi") }
            )
            Image(
                painter = painterResource(R.drawable.bhojpuri),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                    .width(180.dp)
                    .height(200.dp)
                    .border(10.dp, Color(0xFF2196F3))
                    .clickable { navController.navigate("bhojpuri") }
            )
        }
        //Spacer(modifier=Modifier.height(13.dp))
        Row(){

            Image(
                painter = painterResource(R.drawable.enne),
                contentDescription = null,
                modifier = Modifier
                    .padding( start = 7.dp, top = 10.dp)
                    .width(175.dp)
                    .height(200.dp)
                    .border(10.dp,Color(0xFFFF0000))
                    .clickable { navController.navigate("english") }
            )
            Image(
                painter = painterResource(R.drawable.rap),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                    .width(180.dp)
                    .height(200.dp)
                    .border(10.dp, Color(0xFF000000))
                    .clickable { navController.navigate("rap") }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Image(
                painter = painterResource(id = R.drawable.baseline_account_circle_24),
                contentDescription = "Account Circle Icon",
                modifier = Modifier.size(48.dp).padding(start =7.dp)
                    .clickable { navController.navigate("chat_scree/$userName") }
            )
            Spacer(modifier=Modifier.width(20.dp))
            Image(painter = painterResource(id = R.drawable.baseline_favorite_24),
                contentDescription = "favorite",modifier=Modifier.size(48.dp).padding(start = 7.dp))
            Spacer(modifier=Modifier.width(20.dp))
            Image(painter = painterResource(id = R.drawable.baseline_settings_24), contentDescription = null,
            modifier=Modifier.size(48.dp).padding(start = 7.dp))
            Spacer(modifier=Modifier.width(20.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(end = 10.dp)
                    .border(
                        width = 8.dp,
                        color =Color(0xFF800080),
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
                    .clickable { navController.navigate("ak") }
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Reel", color = Color.White)
            }
        }
    }
}