package com.undira.annet.adapter.main.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.undira.annet.databinding.ComponentStatusCardBinding
import com.undira.annet.model.Post

class RecyclerViewAdapter(
    val data: ArrayList<Post>
): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    abstract class ViewHolder(val view: ComponentStatusCardBinding ): RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}