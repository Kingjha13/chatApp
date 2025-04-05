package com.example.animationdemo.ui.theme

data class ChatMessage(
    val id: String = "",
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val senderId: String = ""
)