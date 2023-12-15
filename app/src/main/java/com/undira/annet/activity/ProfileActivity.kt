package com.undira.annet.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.undira.annet.R
import com.undira.annet.adapter.profile.RecyclerViewAdapter
import com.undira.annet.databinding.ActivityProfileBinding
import com.undira.annet.view_model.profile.ViewModel

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this@ProfileActivity)[ViewModel::class.java]

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = "Profile"
        }

        initializeRecyclerView()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun initializeRecyclerView(){
        binding.recyclerView.adapter = RecyclerViewAdapter(this@ProfileActivity, viewModel.dataPost)
        binding.recyclerView.layoutManager = LinearLayoutManager(this@ProfileActivity)
    }
}