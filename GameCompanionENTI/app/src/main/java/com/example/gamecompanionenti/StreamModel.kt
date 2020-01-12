package com.example.gamecompanionenti

import com.google.gson.annotations.SerializedName

data class StreamModel (
    val id: String? = null,
    @SerializedName("user_id") val userId: String? = null,
    @SerializedName("user_name") val username: String? = null,
    @SerializedName("game_id") val gameId: String? = null,
    val title:String? = null,
    @SerializedName("viewer_count") val viewerCount: Int? = null,
    @SerializedName("thumbnail_url") val thumbnailUrl: String? = null
){
    fun getSmallThumbnailUrl():String?{
    return  thumbnailUrl?.replace("{width}","500")?.replace("{height}","250")
    }
}

data class StreamResponse(
    @SerializedName("data")val results: List<StreamModel>? = null,
    val pagination:TwitchPagination? = null
){

}

data class TwitchPagination (
    val cursor: String? = null
)