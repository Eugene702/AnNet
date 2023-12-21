package com.undira.annet.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.undira.annet.adapter.detail_post.RecyclerViewAdapter
import com.undira.annet.databinding.ActivityDetailPostBinding
import com.undira.annet.model.CommentInput
import com.undira.annet.model.CommentList
import com.undira.annet.model.PostGetAll
import com.undira.annet.view_model.detail_post.ViewModel
import kotlinx.coroutines.launch

class DetailPostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPostBinding
    private lateinit var viewModel: ViewModel
    private var dataPost: PostGetAll? = null
    companion object{
        const val DETAIL_POST: String = "POST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this@DetailPostActivity)[ViewModel::class.java]
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = "Detail Post"
        }

        dataPost = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(DETAIL_POST, PostGetAll::class.java)
        } else {
            intent.getParcelableExtra(DETAIL_POST)
        }

        if(dataPost != null){
            initializeData()
            formComment(idPost = dataPost!!.id)
            initializeRecyclerView(idPost = dataPost!!.id)
        }
    }

    private fun initializeData(){
        val profileUrl = "https://ui-avatars.com/api/?name=${dataPost?.users?.name}"
        Glide.with(this@DetailPostActivity)
            .load(profileUrl)
            .into(binding.avatar)

        binding.nameOwner.text = dataPost?.users?.name
        binding.content.text = dataPost?.content
    }
    private fun formComment(idPost: String){
        binding.sendCommentBtn.setOnClickListener {
            binding.commentInputLayout.isErrorEnabled = false
            if(binding.commentInput.text.toString().trim().isNotEmpty()){
                lifecycleScope.launch {
                    binding.sendCommentBtn.isEnabled = false
                    if(dataPost != null){
                        viewModel.addComment(CommentInput(dataPost!!.id, dataPost!!.id_users, binding.commentInput.text.toString()))
                        binding.commentInput.text?.clear()
                        initializeRecyclerView(idPost)
                    }
                    binding.sendCommentBtn.isEnabled = true
                }
            }else{
                binding.commentInputLayout.error = "Input cannot be empty!"
            }
        }
    }

    private fun initializeRecyclerView(idPost: String){
        lifecycleScope.launch {
            val data: List<CommentList> = viewModel.getAllComment(idPost)
            binding.recyclerView.adapter = RecyclerViewAdapter(this@DetailPostActivity, data)
            binding.recyclerView.layoutManager = LinearLayoutManager(this@DetailPostActivity)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}