package com.undira.annet.adapter.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.undira.annet.databinding.ComponentStatusCardBinding
import com.undira.annet.model.Post

class RecyclerViewAdapter(
    private val context: Context,
    private val data: ArrayList<Post>
): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(private val context: Context, private val binding: ComponentStatusCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Post){
            Glide
                .with(context)
                .load(data.avatar)
                .into(binding.avatar)

            binding.name.text = data.name
            binding.content.text = data.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ComponentStatusCardBinding = ComponentStatusCardBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(context, binding)
    }

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}