package com.undira.annet.adapter.main.chat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.undira.annet.activity.ChatActivity
import com.undira.annet.databinding.ComponentInboxListBinding
import com.undira.annet.model.Inbox
import com.undira.annet.utils.convertTimestampToDate

class RecyclerViewAdapter(
    private val context: Context,
    private val data: List<Inbox>
): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(private val context: Context, private val binding: ComponentInboxListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Inbox){
            val profileUrl = "https://ui-avatars.com/api/?name=${data.user_name}"
            Glide.with(context)
                .load(profileUrl)
                .into(binding.avatar)

            binding.date.text = convertTimestampToDate(data.message_date)
            binding.name.text = data.user_name
            binding.message.text = data.messages

            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(ChatActivity.USER_OPPONENT, data.user_id)
            binding.cardview.setOnClickListener { context.startActivity(intent) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ComponentInboxListBinding = ComponentInboxListBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(context, binding)
    }

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}