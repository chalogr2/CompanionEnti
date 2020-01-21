package com.example.gamecompanionenti

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_event_one.*


class EventOneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_one)
        val startingIntent = intent
        val sentText = startingIntent.getStringExtra("newsText")
        val sentUrl = startingIntent.getStringExtra("imageUrl")

        textView10.text = sentText
        if(sentUrl!="") {
            Glide
                .with(this)
                .load(sentUrl)
                .apply(
                    RequestOptions()
                        .transforms(CenterCrop())
                        .placeholder(R.drawable.ic_profile)
                )
                .into(imageViewt)
        }

    }
}
