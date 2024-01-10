package com.undira.annet.adapter.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.undira.annet.databinding.ComponentBubbleChatBinding
import com.undira.annet.model.Chat

class RecyclerViewAdapter(
    private val context: Context,
    private val data: List<Chat>,
    private val idUser: String
): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(private val context: Context, val binding: ComponentBubbleChatBinding, val idUser: String): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Chat){
            if(idUser == data.id_user){
                binding.chatMe.visibility = View.VISIBLE
                binding.textChatMe.text = data.messages
            }else{
                binding.chatOpponent.visibility = View.VISIBLE
                binding.textChatOpponent.text = data.messages
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ComponentBubbleChatBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(context, binding, idUser)
    }

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}