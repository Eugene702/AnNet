package com.undira.annet.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.undira.annet.adapter.search.RecyclerViewAdapter
import com.undira.annet.databinding.ActivitySearchBinding
import com.undira.annet.view_model.search.ViewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private  lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this@SearchActivity)[ViewModel::class.java]

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = null
        }

        initializeRecyclerView()
    }

    private fun initializeRecyclerView(){
        binding.recyclerView.adapter = RecyclerViewAdapter(this@SearchActivity, viewModel.data)
        binding.recyclerView.layoutManager = LinearLayoutManager(this@SearchActivity)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}