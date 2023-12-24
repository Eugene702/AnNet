package com.undira.annet.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.undira.annet.R
import com.undira.annet.adapter.profile.RecyclerViewAdapter
import com.undira.annet.databinding.ActivityProfileBinding
import com.undira.annet.model.PostGetAll
import com.undira.annet.store.UserStore
import com.undira.annet.view_model.profile.ViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ViewModel
    private lateinit var userStore: UserStore
    private var userUUID: String? = null

    companion object{
        const val USER_ID = "UUID_USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this@ProfileActivity)[ViewModel::class.java]
        userStore = UserStore(this@ProfileActivity)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayHomeAsUpEnabled(true)
            title = "Profile"
        }

        userUUID = intent.getStringExtra(USER_ID)
        if(userUUID != null){
            lifecycleScope.launch {
                val userSignIn: String? = userStore.getUuid.first()

                if(userSignIn != userUUID){
                    getUserDataFromServer()
                }else{
                    getUserDataFromStore()
                }

                initializeRecyclerView()
            }
        }else{
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.signout -> {
                MaterialAlertDialogBuilder(this@ProfileActivity)
                    .setTitle("Sign out!")
                    .setMessage("Are you sure you want to log out?")
                    .setNegativeButton("No, cancel!") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("Yes, I'm sure!") { _, _ ->
                        lifecycleScope.launch {
                            userStore.resetUser()
                            val intent  = Intent(this@ProfileActivity, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                    }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private suspend fun initializeRecyclerView(){
        if(userUUID != null){
            val postData: List<PostGetAll> = viewModel.getStatusProfile(userUUID!!)
            binding.recyclerView.adapter = RecyclerViewAdapter(this@ProfileActivity, postData)
            binding.recyclerView.layoutManager = LinearLayoutManager(this@ProfileActivity)
        }
    }

    private suspend fun getUserDataFromServer(){
        val userData = userUUID?.let { viewModel.getUser(it) }
        val profileUrl = "https://ui-avatars.com/api/?name=${userData?.name}"

        Glide.with(this@ProfileActivity)
            .load(profileUrl)
            .into(binding.avatarProfile)

        binding.nameProfile.text = userData?.name
        binding.emailProfile.text = userData?.email

        binding.messageBtn.setOnClickListener {
            val intent = Intent(this@ProfileActivity, ChatActivity::class.java)
            intent.putExtra(ChatActivity.USER_OPPONENT, userData?.id)
            startActivity(intent)
        }
    }

    private suspend fun getUserDataFromStore(){
        val userName: String? = userStore.getName.first()
        val userEmail: String? = userStore.getEmail.first()
        val profileUrl = "https://ui-avatars.com/api/?name=${userName}"

        Glide.with(this@ProfileActivity)
            .load(profileUrl)
            .into(binding.avatarProfile)

        binding.nameProfile.text = userName
        binding.emailProfile.text = userEmail

        binding.messageBtn.visibility = View.GONE
    }
}