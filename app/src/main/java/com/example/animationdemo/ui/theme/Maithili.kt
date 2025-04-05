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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.animationdemo.R
@Composable
fun Maithili(modifier: Modifier, navController: NavHostController) {
    Column (modifier=Modifier.background(Color(0xFFBBDEFB))){
        Row(
            modifier = Modifier
                .padding(top = 25.dp)
        ) {
            TextButton(
                onClick = {
                },
                modifier = Modifier
                    .padding(start = 142.dp)
                    .background(Color(0xFF003366), shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Text(
                    text = "Search here\uD83D\uDD0D",
                    color = Color.White
                )
            }

        }
        Image(
            painter = painterResource(R.drawable.ms1),
            contentDescription = null,
            modifier = Modifier
                .padding( start = 11.dp, top = 9.dp, end = 11.dp)
                .width(384.dp)
                .height(220.dp)
                .border(10.dp,Color(0xFFFC3AA0))
                .clickable { navController.navigate("video/ma") }
        )
        Image(
            painter = painterResource(R.drawable.ms2),
            contentDescription = null,
            modifier = Modifier
                .padding( start = 11.dp, top = 12.dp, end = 11.dp)
                .width(384.dp)
                .height(220.dp)
                .border(10.dp,Color(0xFF800080))
                .clickable { navController.navigate("video/mb") }
        )
        Image(
            painter = painterResource(R.drawable.ms3),
            contentDescription = null,
            modifier = Modifier
                .padding( start = 11.dp, top = 12.dp, end = 11.dp)
                .width(384.dp)
                .height(220.dp)
                .border(10.dp, Color(0xFF8A2BE2))
                .clickable { navController.navigate("video/mc") }
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "⬇⬇More⬇⬇", color = Color.White, modifier = Modifier
                .padding(start = 142.dp)
                .background(Color(0xFF003366))
        )
    }
}