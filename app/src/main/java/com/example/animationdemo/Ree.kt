package com.example.animationdemo

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import androidx.core.net.toUri
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.navigation.NavHostController
import com.google.firebase.database.*

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun ReelScreen(modifier: Modifier = Modifier, navController: NavHostController) {
    val videoList= listOf("new")
    var isTra by remember { mutableStateOf(false) }
    var currentVideo by remember { mutableStateOf(0) }
    val firebaseDatabase = FirebaseDatabase.getInstance()
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }

    val playerView = remember {
        PlayerView(context).apply {
            player = exoPlayer
            useController = true
        }
    }
    var videoUrl by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(videoList[currentVideo]) {
        val databaseReference = firebaseDatabase.getReference("/").child(videoList[currentVideo])
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
    videoUrl?.let {
        LaunchedEffect(it) {
            initializeExoplayerView(exoPlayer, playerView, it)
        }
    }
    exoPlayer.playWhenReady = true
    exoPlayer.addListener(object : Player.Listener {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            if (playbackState == Player.STATE_ENDED && !isTra) {
                isTra=true
                currentVideo = if (currentVideo < videoList.size - 1) {
                    currentVideo + 1
                } else {
                    0
                }
                isTra = false
            }
        }
    })
    Text(text = "Explore the latest Reels!", color = Color.White, modifier = Modifier.padding(start =
    18.dp, top = 740.dp).background(Color.Magenta))
    TextButton(
        onClick = {
        },
        modifier = Modifier
            .padding(start = 240.dp, top = 733.dp)
            .background(Color(0xFF003366), shape = RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
    ) {
        Text(
            text = "NEXT➡",
            color = Color.White
        )
    }
    Box(modifier = Modifier) {
        AndroidView(
            factory = {
                playerView
            },
            modifier = modifier
        )
    }
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

        Log.d("ExoPlayer", "Player setup complete")
    } catch (e: Exception) {
        Log.e("ExoPlayer", "Error initializing ExoPlayer: $e")
    }
}
