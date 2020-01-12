package com.example.gamecompanionenti


import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_one.*
import kotlinx.android.synthetic.main.fragment_streams_fragmnet.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.net.URL


/**
 * A simple [Fragment] subclass.
 */
class StreamsFragmnet : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_streams_fragmnet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(IO).launch{
            getStreams()
        }

    }

    private suspend fun getStreams(){

        TwitchApiService.endpoints.getStreams(gameid = "369588").enqueue(object : retrofit2.Callback<StreamResponse>{

            override fun onFailure(call: retrofit2.Call<StreamResponse>, t: Throwable) {
                Log.w("StreamsFragmnet",t)
            }

            override fun onResponse(call: retrofit2.Call<StreamResponse>, response: retrofit2.Response<StreamResponse>) {
                Log.i("StreamsFragmnet","++ onResponse ++")
                if(response.isSuccessful) {
                    //All good
                    val streams = response.body()?.results?: emptyList()

                    CoroutineScope(IO).launch{
                        getGamesFromStream(streams)
                    }

                    val imageUri = streams[0].getSmallThumbnailUrl()
                    val ivBasicImage: ImageView = image1Twitch as ImageView
                    Picasso.with(context).load(imageUri).into(ivBasicImage)
                    video1Title.text = streams[0].title

                    val imageUri2 = streams[1].getSmallThumbnailUrl()
                    val ivBasicImage2: ImageView = image2Twitch as ImageView
                    Picasso.with(context).load(imageUri2).into(ivBasicImage2)
                    video2Title.text = streams[1].title

                    val imageUri3 = streams[2].getSmallThumbnailUrl()
                    val ivBasicImage3: ImageView = image3Twitch as ImageView
                    Picasso.with(context).load(imageUri3).into(ivBasicImage3)
                    video3Title.text = streams[2].title


                    Log.i("StreamsFragmnet",streams.toString() )
                }else
                {
                    //Not good
                    Log.w("StreamsFragmnet",response.message())
                }
            }
        })
    }

    private suspend fun getGamesFromStream(streams:List<StreamModel>){

        val ids = streams.map{it.gameId ?:""}
        TwitchApiService.endpoints.getGames(gameIds = ids).enqueue(object:retrofit2.Callback<GameResponse> {
            override fun onFailure(call: Call<GameResponse>, t: Throwable) {
                Log.w("StreamsFragmnet",t)
            }

            override fun onResponse(call: Call<GameResponse>, response: Response<GameResponse>) {
                if(response.isSuccessful) {
                    val games = response.body()?.results?: emptyList()
                    video1Game.text = games[0].name
                    video2Game.text = games[0].name
                    video3Game.text = games[0].name
                }
            }
        })
    }


}
