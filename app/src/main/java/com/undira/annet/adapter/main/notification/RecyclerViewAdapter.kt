package com.undira.annet.adapter.main.notification

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.undira.annet.databinding.ComponentNotificationListBinding
import com.undira.annet.model.Notification

class RecyclerViewAdapter(private val context: Context, private val data: ArrayList<Notification>): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(private val context: Context, private val binding: ComponentNotificationListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Notification){
            binding.title.text = data.title
            binding.description.text = data.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ComponentNotificationListBinding = ComponentNotificationListBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(context, binding)
    }

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}