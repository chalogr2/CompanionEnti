package com.example.gamecompanionenti

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.layout_message.view.*
import kotlinx.android.synthetic.main.layout_news.view.*

class RecyclerAdapter (var list:List<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val senderr = itemView.senderS
        val messager = itemView.messageS
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.layout_message,parent,false)
        return ViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return list.count()
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val ctx = holder.itemView.context
        val msg1 = list[position].text
        val msg2 = list[position].userName
        val dts1 = list[position].createdAt.toString()
        val imgurl = list[position].imageUrl
        holder?.itemView?.messageS?.text = msg1
        holder?.itemView?.senderS?.text = msg2
        holder?.itemView?.dateS?.text = dts1
        if(imgurl!="") {
            Glide
                .with(ctx)
                .load(imgurl)
                .apply(
                    RequestOptions()
                        .transforms(CenterCrop())
                        .placeholder(R.drawable.ic_profile)
                )
                .into(holder?.itemView?.chatpic)
        }
        holder?.itemView?.messageS?.setBackgroundColor(Color.LTGRAY)
        holder?.itemView?.dateS?.setBackgroundColor(Color.LTGRAY)
        holder?.itemView?.senderS?.setBackgroundColor(ctx.getColor( R.color.colorAccent))
    }

}
