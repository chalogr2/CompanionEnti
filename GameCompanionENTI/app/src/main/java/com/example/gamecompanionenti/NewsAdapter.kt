package com.example.gamecompanionenti

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_news.view.*


class NewsAdapter(): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    val backgrounds = listOf(R.drawable.mainimage4,R.drawable.mainimage2, R.drawable.mainimage1)
    val linksto = listOf(Signin::class.java,Signin::class.java,Signin::class.java)

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
        val backg = backgrounds[position]
        holder?.view?.menuButton?.background = ctx.getDrawable(backg)
        btn.setOnClickListener {
            val intent = Intent(btn.context, linksto[position])
            btn.context.startActivity(intent)
        }

    }


}