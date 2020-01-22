package com.example.gamecompanionenti

import com.google.gson.annotations.SerializedName

data class TwitchUserModel(
    val id: String? = null,
    val login: String? = null,
    val description: String? = null
)

data class TwitchUserResponse(
    @SerializedName("data") val results: List<TwitchUserModel>? = null
)