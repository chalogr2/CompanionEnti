package com.example.gamecompanionenti


import com.google.android.gms.common.internal.ServiceSpecificExtraArgs
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface TwitchApiService{

    @Headers("Client-ID: vl66hntkqvny64c1llc54dlpllpppr")
    @GET("streams")
    fun getStreams(@Query("game_id")gameid:String? = null):retrofit2.Call<StreamResponse>

    @Headers("Client-ID: vl66hntkqvny64c1llc54dlpllpppr")
    @GET("games")
    fun getGames(@Query("id")gameIds: List<String>? = null):retrofit2.Call<GameResponse>

    @Headers("Client-ID: vl66hntkqvny64c1llc54dlpllpppr")
    @GET("users")
    fun getUsers(@Query("id")gameIds: List<String>? = null):retrofit2.Call<TwitchUserResponse>



    //create https client
    companion object{
        private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.twitch.tv/helix/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //endpoints:
        var endpoints = retrofit.create<TwitchApiService>(TwitchApiService::class.java)
    }
}