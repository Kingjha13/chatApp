package com.example.animationdemo.ui.theme

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.regex.Pattern

//@Composable
//fun ChatScreen(
//    modifier: Modifier,
//    navController: NavHostController,
//    authViewModel: AuthViewModel,
//    userName: String
//) {
//    var messageText by remember { mutableStateOf("") }
//    val firestore = FirebaseFirestore.getInstance()
//    val messagesCollection = firestore.collection("messages")
//    val messages = remember { mutableStateListOf<ChatMessage>() }
//
//    var countDownTimer by remember { mutableStateOf<CountDownTimer?>(null) }
//
//    LaunchedEffect(Unit) {
//        countDownTimer?.cancel()
//        countDownTimer = object : CountDownTimer(5 * 60 * 1000L, 1000L) {
//            override fun onTick(millisUntilFinished: Long) {}
//
//            override fun onFinish() {
//                navController.navigate("login")
//            }
//        }.apply {
//            start()
//        }
//    }
//
//    LaunchedEffect(messageText) {
//        countDownTimer?.cancel()
//        countDownTimer?.start()
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text("Welcome, $userName!", style = MaterialTheme.typography.bodyMedium)
//
//        LazyColumn(
//            modifier = Modifier
//                .weight(1f)
//                .fillMaxWidth(),
//            verticalArrangement = Arrangement.spacedBy(8.dp),
//            contentPadding = PaddingValues(bottom = 16.dp)
//        ) {
//            items(messages.size) { index ->
//                val message = messages[index]
//                Text("${message.senderId}: ${message.message}")
//            }
//        }
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            TextField(
//                value = messageText,
//                onValueChange = { messageText = it },
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(vertical = 8.dp)
//                    .padding(16.dp),
//                shape = MaterialTheme.shapes.medium,
//                placeholder = { Text(text = "Type a message...") }
//            )
//
//            Button(
//                onClick = {
//                    if (messageText.isNotEmpty()) {
//                        val message = ChatMessage(
//                            id = messagesCollection.document().id,
//                            message = messageText,
//                            senderId = userName
//                        )
//                        messagesCollection.document(message.id).set(message)
//                        messageText = ""
//                    } else {
//                        Toast.makeText(
//                            navController.context, "Message cannot be empty", Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                },
//                modifier = Modifier.align(Alignment.CenterVertically)
//            ) {
//                Text("➡")
//            }
//        }
//    }
//
//    LaunchedEffect(Unit) {
//        messagesCollection.orderBy("timestamp", Query.Direction.ASCENDING)
//            .addSnapshotListener { snapshot, e ->
//                if (e != null) {
//                    Toast.makeText(navController.context, "Unable to get new messages", Toast.LENGTH_SHORT).show()
//                    return@addSnapshotListener
//                }
//                if (snapshot != null && !snapshot.isEmpty) {
//                    messages.clear()
//                    for (document in snapshot.documents) {
//                        val message = document.toObject(ChatMessage::class.java)
//                        if (message != null) {
//                            messages.add(message)
//                        }
//                    }
//                }
//            }
//    }
//}


@Composable
fun ChatScreen(
    modifier: Modifier,
    navController: NavHostController,
    userName: String,
    authViewModel: AuthViewModel
) {
    var messageText by remember { mutableStateOf("") }
    val firestore = FirebaseFirestore.getInstance()
    val messagesCollection = firestore.collection("messages")
    val messages = remember { mutableStateListOf<ChatMessage>() }

    val listState = rememberLazyListState()

    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.scrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Welcome, $userName!", style = MaterialTheme.typography.bodyMedium)

        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(messages.size) { index ->
                val message = messages[index]
                MessageWithLinks(message.senderId, message.message)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp).padding(start = 0.dp)
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium,
                placeholder = { Text(text = "Type a message...") },
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (messageText.isNotEmpty()) {
                            val message = ChatMessage(
                                id = messagesCollection.document().id,
                                message = messageText,
                                senderId = userName
                            )
                            messagesCollection.document(message.id).set(message)
                            messageText = ""
                        } else {
                            Toast.makeText(
                                navController.context, "Message cannot be empty", Toast.LENGTH_SHORT
                            ).show()
                        }
                        keyboardController?.hide() // Hide keyboard after pressing Done
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done // This tells the system to show the "Done" button on the keyboard
                )
            )
            Button(
                onClick = {
                    if (messageText.isNotEmpty()) {
                        val message = ChatMessage(
                            id = messagesCollection.document().id,
                            message = messageText,
                            senderId = userName
                        )
                        messagesCollection.document(message.id).set(message)
                        messageText = ""
                    } else {
                        Toast.makeText(
                            navController.context, "Message cannot be empty", Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.align(Alignment.CenterVertically),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text("➡")
            }
        }
    }

    LaunchedEffect(Unit) {
        messagesCollection.orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Toast.makeText(navController.context, "Unable to get new messages", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    messages.clear()
                    for (document in snapshot.documents) {
                        val message = document.toObject(ChatMessage::class.java)
                        if (message != null) {
                            messages.add(message)
                        }
                    }
                }
            }
    }
}

@Composable
fun MessageWithLinks(senderId: String, message: String) {
    val context = LocalContext.current
    val fullMessage = "$senderId: $message"
    TextWithLinks(fullMessage, context)
}

@Composable
fun TextWithLinks(text: String, context: Context) {
    val urlPattern = Pattern.compile(
        "(https?://[\\w-]+(?:\\.[\\w-]+)+[/#?]?.*)",
        Pattern.CASE_INSENSITIVE
    )

    val annotatedString = buildAnnotatedString {
        var lastIndex = 0
        val matcher = urlPattern.matcher(text)
        while (matcher.find()) {
            append(text.substring(lastIndex, matcher.start()))
            pushStringAnnotation(tag = "URL", annotation = matcher.group())
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append(matcher.group())
            }
            pop()
            lastIndex = matcher.end()
        }
        append(text.substring(lastIndex))
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.let { annotation ->
                    val url = annotation.item
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
        }
    )
}





//@Composable
//fun ChatScreen(
//    modifier: Modifier,
//    navController: NavHostController,
//    userName: String,
//    authViewModel: AuthViewModel,
//) {
//    var messageText by remember { mutableStateOf("") }
//    val firestore = FirebaseFirestore.getInstance()
//    val messagesCollection = firestore.collection("messages")
//    val messages = remember { mutableStateListOf<ChatMessage>() }
//
//    val listState = rememberLazyListState()
//    val context = LocalContext.current
//
//    // State for storing the selected image/video URI
//    var selectedMediaUri by remember { mutableStateOf<Uri?>(null) }
//
//    // Cloudinary configuration
//    val cloudinary = Cloudinary(
//        ObjectUtils.asMap(
//        "cloud_name", "deegru9la",
//        "api_key", "693796868383768",
//        "api_secret", "pl0v8ZAVeQYy1VmrQkV1LMZmq8s"
//    ))
//
//    // Launcher for picking an image or video
//    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//        if (uri != null) {
//            selectedMediaUri = uri
//            // Upload the selected media to Cloudinary
//            uploadMediaToCloudinary(uri)
//        }
//    }
//
//    // Upload the media to Cloudinary
//    fun uploadMediaToCloudinary(uri: Uri) {
//        try {
//            val filePath = uri.path
//            val options = ObjectUtils.asMap("public_id", UUID.randomUUID().toString())
//            cloudinary.uploader().upload(uri, options).let { uploadResult ->
//                val mediaUrl = uploadResult["secure_url"] as String
//                saveMediaToFirestore(mediaUrl)
//            }
//        } catch (e: Exception) {
//            Toast.makeText(context, "Error uploading media: ${e.message}", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    // Save media URL to Firestore
//    fun saveMediaToFirestore(mediaUrl: String) {
//        val message = ChatMessage(
//            id = messagesCollection.document().id,
//            message = "", // No text, just media
//            senderId = userName,
//            mediaUrl = mediaUrl // Store the URL in Firestore
//        )
//        messagesCollection.document(message.id).set(message)
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text("Welcome, $userName!", style = MaterialTheme.typography.bodyMedium)
//
//        // LazyColumn to display chat messages
//        LazyColumn(
//            state = listState,
//            modifier = Modifier
//                .weight(1f)
//                .fillMaxWidth(),
//            verticalArrangement = Arrangement.spacedBy(8.dp),
//            contentPadding = PaddingValues(bottom = 16.dp)
//        ) {
//            items(messages.size) { index ->
//                val message = messages[index]
//                if (message.mediaUrl.isNullOrEmpty()) {
//                    Text(message.message)
//                } else {
//                    // Display media (image or video)
//                    MediaMessage(mediaUrl = message.mediaUrl)
//                }
//            }
//        }
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            // TextField to type messages
//            TextField(
//                value = messageText,
//                onValueChange = { messageText = it },
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(vertical = 8.dp)
//                    .padding(16.dp),
//                shape = MaterialTheme.shapes.medium,
//                placeholder = { Text(text = "Type a message...") }
//            )
//
//            // Button for "Send"
//            Button(
//                onClick = {
//                    if (messageText.isNotEmpty()) {
//                        val message = ChatMessage(
//                            id = messagesCollection.document().id,
//                            message = messageText,
//                            senderId = userName
//                        )
//                        messagesCollection.document(message.id).set(message)
//                        messageText = ""
//                    } else {
//                        Toast.makeText(
//                            context, "Message cannot be empty", Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                },
//                modifier = Modifier.align(Alignment.CenterVertically)
//            ) {
//                Text("➡")
//            }
//
//            // Icon button for selecting photos/videos
//            IconButton(
//                onClick = {
//                    launcher.launch("image/*") // You can also allow video by using "image/* video/*"
//                }
//            ) {
//                Icon(
//                    imageVector = Icons.Default.AddPhotoAlternate,
//                    contentDescription = "Attach Photo/Video"
//                )
//            }
//        }
//    }
//
//    // Listener to fetch messages from Firestore
//    LaunchedEffect(Unit) {
//        messagesCollection.orderBy("timestamp", Query.Direction.ASCENDING)
//            .addSnapshotListener { snapshot, e ->
//                if (e != null) {
//                    Toast.makeText(context, "Unable to get new messages", Toast.LENGTH_SHORT).show()
//                    return@addSnapshotListener
//                }
//                if (snapshot != null && !snapshot.isEmpty) {
//                    messages.clear()
//                    for (document in snapshot.documents) {
//                        val message = document.toObject(ChatMessage::class.java)
//                        if (message != null) {
//                            messages.add(message)
//                        }
//                    }
//                }
//            }
//    }
//}
//
//// Composable to display media (image/video)
//@Composable
//fun MediaMessage(mediaUrl: String) {
//    // Display image or video depending on URL type
//    val isImage = mediaUrl.endsWith(".jpg") || mediaUrl.endsWith(".png")
//    if (isImage) {
//        Image(painter = rememberImagePainter(mediaUrl), contentDescription = null)
//    } else {
//        Text("Video URL: $mediaUrl")
//    }
//}