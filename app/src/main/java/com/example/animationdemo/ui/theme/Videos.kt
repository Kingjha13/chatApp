package com.example.animationdemo

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import androidx.core.net.toUri
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.navigation.NavHostController
import com.google.firebase.database.*

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun Videos(videoUrl: String,modifier: Modifier = Modifier, navController: NavHostController) {
    val firebaseDatabase = FirebaseDatabase.getInstance()
    var isSelected by remember { mutableStateOf(true) }

    val databaseReference = firebaseDatabase.getReference("$videoUrl")
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    val playerView = remember {
        PlayerView(context).apply {
            player = exoPlayer
            resizeMode= AspectRatioFrameLayout.RESIZE_MODE_FIT
        }
    }
    var videoUrl by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fetchedUrl = snapshot.getValue(String::class.java)
                if (fetchedUrl != null) {
                    // Update the video URL state with the Firebase value
                    videoUrl = fetchedUrl
                } else {
                    Toast.makeText(context, "No video URL found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Failed to get video URL", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // When the video URL is fetched, initialize ExoPlayer with the URL
    videoUrl?.let {
        LaunchedEffect(it) {
            initializeExoplayerView(exoPlayer, playerView, it)
        }
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = if (isSelected) R.drawable.baseline_favorite_border_24 else R.drawable.baseline_favorite_24),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 23.dp, top = 247.dp)
                    .clickable { isSelected = !isSelected }
                    .size(38.dp)
            )
            Text(text = "++favorite", color = Color.Magenta, modifier = Modifier.padding(top = 8.dp,start=23.dp))
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_file_download_24),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 60.dp, top = 247.dp)
                    .size(38.dp)
            )
            Text(text = "download", color = Color.Blue, modifier = Modifier.padding(top = 8.dp, start = 60.dp))
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_share_24),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 60.dp, top = 247.dp)
                    .size(38.dp)
            )
            Text(text = "share", color = Color.Green, modifier = Modifier.padding(top = 8.dp, start = 60.dp),
                fontWeight = FontWeight.Bold)
        }
    }

    AndroidView(
        factory = {
            playerView
        },
        modifier = modifier
    )
    // Clean up ExoPlayer when composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
            Log.d("ExoPlayer", "ExoPlayer released")
        }
    }
}

// Function to initialize ExoPlayer with a media URL
@androidx.annotation.OptIn(UnstableApi::class)
@OptIn(UnstableApi::class)
private fun initializeExoplayerView(
    exoPlayer: ExoPlayer,
    playerView: PlayerView,
    videoUrl: String,
) {
    try {
        Log.d("ExoPlayer", "Initializing ExoPlayer with URL: $videoUrl")

        // Create a MediaItem from the video URL
        val mediaItem = MediaItem.fromUri(videoUrl.toUri())

        // Create an HTTP DataSource
        val dataSourceFactory = DefaultHttpDataSource.Factory()

        // Create a MediaSource using the media item and data source factory
        val mediaSource = DefaultMediaSourceFactory(dataSourceFactory)
            .createMediaSource(mediaItem)

        // Set up the PlayerView with the ExoPlayer
        playerView.player = exoPlayer

        // Prepare the ExoPlayer with the media source
        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.prepare()

        // Start playing the video
        exoPlayer.playWhenReady = true

        Log.d("ExoPlayer", "Player setup complete")
    } catch (e: Exception) {
        Log.e("ExoPlayer", "Error initializing ExoPlayer: $e")
    }
}