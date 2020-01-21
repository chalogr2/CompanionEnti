package com.example.gamecompanionenti
import java.util.Date

data class Message(var text: String? = null,
                   var createdAt: Date? = null,
                   var userName: String? = null,
                   var userID: String? = null,
                   var imageUrl: String? = null
                    ) {
}