package com.example.gamecompanionenti

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_streams.view.*
import kotlinx.android.synthetic.main.layout_news.view.*


class StreamsAdapter(var list:List<StreamModel>, var gameList:List<String?>, var detailList:List<String?>): RecyclerView.Adapter<StreamsAdapter.ViewHolder>() {

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val button = itemView.menuButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.layout_streams,parent,false)
        return ViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ctx = holder.view.context
        //holder?.view?.streamGame?.text = gameList[position]
        if(list.isNotEmpty()) {
            holder?.view?.streamTitle?.text = list[position]?.title
            if(gameList.isNotEmpty()) {
                holder?.view?.streamGame?.text = gameList[position]?.toString()
            }
            if(detailList.isNotEmpty()){
                holder?.view?.streamUserInfo?.text = detailList[position]?.toString()
            }
            val imgurlt = list[position].getSmallThumbnailUrl()
            val image = holder?.view?.streamImage
            /*if (imgurlt != "") {
                Glide
                    .with(ctx)
                    .load(imgurlt)
                    .apply(
                        RequestOptions()
                            .transforms(CenterCrop())
                            .placeholder(R.drawable.ic_profile)
                    )
                    .into(image)
            }*/
            Picasso.with(ctx).load(imgurlt).into(image)
        }
        //val backg = backgrounds[position]
        //holder?.view?.menuButton?.background = ctx.getDrawable(backg)

    }

    public fun updateList( listy:List<StreamModel>){

        list = listy
        this!!.notifyDataSetChanged()
    }

    public fun updateGameList( gameListy:List<String?>){

        gameList = gameListy
        this!!.notifyDataSetChanged()
    }

    public fun updateDetailList(detailListy:List<String?>){
        detailList = detailListy
        this!!.notifyDataSetChanged()
    }


}