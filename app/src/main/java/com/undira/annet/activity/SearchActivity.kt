package com.undira.annet.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.undira.annet.adapter.search.RecyclerViewAdapter
import com.undira.annet.databinding.ActivitySearchBinding
import com.undira.annet.view_model.search.ViewModel
import kotlinx.coroutines.launch

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

        initializeInput()
        initializeRecyclerView()
    }

    private fun initializeRecyclerView(query: String = ""){
        lifecycleScope.launch {
            val data = viewModel.searchQuery(query)
            binding.recyclerView.adapter = RecyclerViewAdapter(this@SearchActivity, data)
            binding.recyclerView.layoutManager = LinearLayoutManager(this@SearchActivity)
        }
    }

    private fun initializeInput(){
        binding.appbarInput.setOnEditorActionListener { view, actionId, _ ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                if(view.text.trim().isNotEmpty()){
                    initializeRecyclerView(binding.appbarInput.text.toString())
                }
            }

            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}