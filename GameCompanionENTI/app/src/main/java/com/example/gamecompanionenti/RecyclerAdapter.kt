package com.example.gamecompanionenti

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_message.view.*

class RecyclerAdapter (val mesg: Message): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var items: List<Message> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MessageViewHolder(
            LayoutInflater.from(parent?.context).inflate(R.layout.layout_message,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){
            is MessageViewHolder ->{
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int {

        return  items.size
    }

    fun submitList(msgList: List<Message>){

        items = msgList
    }

    class MessageViewHolder constructor(
        itemView: View
    ):RecyclerView.ViewHolder(itemView){
        private val messageSender:TextView = itemView.senderS
        private val messageMessage:TextView = itemView.messageS

        fun bind(msg:Message){
            messageSender.text=msg.userName
            messageMessage.text=msg.text
        }
    }

}
