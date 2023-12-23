package com.undira.annet.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.undira.annet.R
import com.undira.annet.adapter.profile.RecyclerViewAdapter
import com.undira.annet.databinding.ActivityProfileBinding
import com.undira.annet.store.UserStore
import com.undira.annet.view_model.profile.ViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ViewModel
    private lateinit var userStore: UserStore
    private lateinit var userUUID: String

    companion object{
        const val USER_ID = "UUID_USER"
    }

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

        lifecycleScope.launch {
            val userID: String? = userStore.getUuid.first()
            if(userID != null){
                userUUID = userID
            }else{
                binding.messageBtn.visibility = View.GONE
            }
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

    private suspend fun getUserData(){
        val userData = viewModel.getUser(userUUID)
        val profileUrl = "https://ui-avatars.com/api/?name=${userData.data.}"

        binding.avatarProfile
    }
}