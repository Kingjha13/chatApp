package com.example.animationdemo.ui.theme

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import android.Manifest

@Composable
fun CallPhoneScreen(
    modifier: Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    var phoneNumber by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Button click listener
    val onCallClick = {
        if (phoneNumber.isNotEmpty()) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Create an intent to initiate a phone call
                val phoneIntent = Intent(Intent.ACTION_CALL)
                phoneIntent.data = Uri.parse("tel:$phoneNumber")
                context.startActivity(phoneIntent)
            } else {
                // Request permission if not granted
                requestCallPermission(context)
            }
        } else {
            Toast.makeText(context, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
        }
    }

    // UI Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
       // horizontalAlignment = LineHeightStyle.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Enter Phone Number",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onCallClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = phoneNumber.isNotEmpty()
        ) {
            Text(text = "Call")
        }
    }
}
// Request permission for CALL_PHONE if not granted
private fun requestCallPermission(context: Context) {
    val CALL_PHONE_PERMISSION_CODE = 100
    ActivityCompat.requestPermissions(
        context as Activity,
        arrayOf(Manifest.permission.CALL_PHONE),
        CALL_PHONE_PERMISSION_CODE
    )
}

