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
import kotlinx.android.synthetic.main.layout_message.view.*
import kotlinx.android.synthetic.main.layout_news.view.*


class NewsAdapter(var list:List<News>): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val button = itemView.menuButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.layout_news,parent,false)
        return ViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ctx = holder.view.context
        val btn = holder.view.menuButton
        val imgurll = list[position].imageUrl;
        val image = holder?.view?.menuBImage
        if(imgurll!="") {
            Glide
                .with(ctx)
                .load(imgurll)
                .apply(
                    RequestOptions()
                        .transforms(CenterCrop())
                        .placeholder(R.drawable.ic_profile)
                )
                .into(image)
        }
        //val backg = backgrounds[position]
        //holder?.view?.menuButton?.background = ctx.getDrawable(backg)
        btn.setOnClickListener {
            val intent = Intent(btn.context, EventOneActivity::class.java)
            intent.putExtra("newsText", list[position].text)
            intent.putExtra("imageUrl", imgurll)
            btn.context.startActivity(intent)
        }

    }


}