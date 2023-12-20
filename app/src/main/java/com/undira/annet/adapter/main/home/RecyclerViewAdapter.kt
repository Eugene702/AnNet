package com.undira.annet.adapter.main.home

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
    class ViewHolder(private val context: Context, private val view: ComponentStatusCardBinding ): RecyclerView.ViewHolder(view.root){
        fun bind(data: PostGetAll){
            val profileUrl: String = "https://ui-avatars.com/api/?name=${data.users.name}"
            Glide.with(context)
                .load(profileUrl)
                .into(view.avatar)
            view.name.text = data.users.name
            view.content.text = data.content

            val intent = Intent(view.root.context, DetailPostActivity::class.java)
            intent.putExtra(DetailPostActivity.DETAIL_POST, data)
            view.root.setOnClickListener { view.root.context.startActivity(intent) }
            view.commentBtn.setOnClickListener { view.root.context.startActivity(intent) }
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