package com.undira.annet.adapter.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.undira.annet.databinding.ComponentSearchListBinding
import com.undira.annet.model.UserSearch

class RecyclerViewAdapter(
    private val context: Context,
    private val data: ArrayList<UserSearch>
): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(private val context: Context, private val binding: ComponentSearchListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserSearch){
            Glide.with(context)
                .load(data.avatar)
                .into(binding.avatar)

            binding.name.text = data.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ComponentSearchListBinding = ComponentSearchListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(context, binding)
    }

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}