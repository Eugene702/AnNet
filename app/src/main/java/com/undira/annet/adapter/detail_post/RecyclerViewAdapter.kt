package com.undira.annet.adapter.detail_post

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.undira.annet.databinding.ComponentCommentListBinding
import com.undira.annet.model.CommentList

class RecyclerViewAdapter(
    private val context: Context,
    private val data: List<CommentList>
): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(private val context: Context, private val binding: ComponentCommentListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CommentList){
            val profileUrl = "https://ui-avatars.com/api/?name=${data.users.name}"
            Glide.with(context)
                .load(profileUrl)
                .into(binding.avatar)

            binding.date.text = data.date
            binding.name.text = data.users.name
            binding.comment.text = data.comment
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ComponentCommentListBinding = ComponentCommentListBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(context, binding)
    }

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}