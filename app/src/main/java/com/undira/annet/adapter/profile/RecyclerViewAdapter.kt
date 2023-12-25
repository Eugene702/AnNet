package com.undira.annet.adapter.profile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.undira.annet.activity.DetailPostActivity
import com.undira.annet.databinding.ComponentStatusCardBinding
import com.undira.annet.model.PostGetAll

class RecyclerViewAdapter(
    private val context: Context,
    private val data: List<PostGetAll>
): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    class ViewHolder(private val context: Context, private val binding: ComponentStatusCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PostGetAll){
            val profileUrl = "https://ui-avatars.com/api/?name=${data.users.name}"
            Glide
                .with(context)
                .load(profileUrl)
                .into(binding.avatar)

            binding.name.text = data.users.name
            binding.content.text = data.content

            val intent = Intent(binding.root.context, DetailPostActivity::class.java)
            intent.putExtra(DetailPostActivity.DETAIL_POST, data)

            binding.root.setOnClickListener { binding.root.context.startActivity(intent) }
            binding.commentBtn.setOnClickListener { binding.root.context.startActivity(intent) }
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