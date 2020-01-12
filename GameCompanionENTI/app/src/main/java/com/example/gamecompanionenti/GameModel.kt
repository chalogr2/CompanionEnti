package com.example.gamecompanionenti

import com.google.gson.annotations.SerializedName

data class GameModel(
    val id: String? = null,
    val name: String? = null,
    @SerializedName ("box_art_url") val thumbnailUrl: String? = null
)

data class GameResponse(
    @SerializedName("data") val results: List<GameModel>? = null
)