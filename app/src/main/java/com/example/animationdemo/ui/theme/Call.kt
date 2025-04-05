package com.example.animationdemo.ui.theme

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import android.os.Handler
import android.os.Looper
import androidx.compose.ui.graphics.Color

@Composable
fun Call() {
    val phoneNumber = "8084705232"
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Phone Call will be made automatically", color = Color(0xFF0097A7),
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Calling to Person 1", color=Color(0xFF6200EE),
            style = MaterialTheme.typography.bodyLarge
        )

        LaunchedEffect(Unit) {
            Handler(Looper.getMainLooper()).postDelayed({
                makeCall(context, phoneNumber)
            }, 1000)
        }
    }
}

private fun makeCall(context: Context, phoneNumber: String) {
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        val phoneIntent = Intent(Intent.ACTION_CALL)
        phoneIntent.data = Uri.parse("tel:$phoneNumber")
        context.startActivity(phoneIntent)
    } else {
        requestCallPermission(context)
    }
}

private fun requestCallPermission(context: Context) {
    val CALL_PHONE_PERMISSION_CODE = 100
    ActivityCompat.requestPermissions(
        context as Activity,
        arrayOf(Manifest.permission.CALL_PHONE),
        CALL_PHONE_PERMISSION_CODE
    )
}
