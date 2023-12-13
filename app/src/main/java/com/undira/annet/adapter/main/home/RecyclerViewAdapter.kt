package com.undira.annet.adapter.main.home

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
    class ViewHolder(private val context: Context, private val view: ComponentStatusCardBinding ): RecyclerView.ViewHolder(view.root){
        fun bind(data: Post){
            Glide.with(context)
                .load(data.avatar)
                .into(view.avatar)
            view.name.text = data.name
            view.content.text = data.content
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        val binding: ComponentStatusCardBinding = ComponentStatusCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(context, binding)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int  = data.size
}