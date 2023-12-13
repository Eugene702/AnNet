package com.undira.annet.adapter.main.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.undira.annet.databinding.ComponentInboxListBinding
import com.undira.annet.model.Inbox

class RecyclerViewAdapter(
    private val context: Context,
    private val data: ArrayList<Inbox>
): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(private val context: Context, private val binding: ComponentInboxListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Inbox){
            Glide.with(context)
                .load(data.avatar)
                .into(binding.avatar)

            binding.date.text = data.date
            binding.name.text = data.name
            binding.message.text = data.message
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