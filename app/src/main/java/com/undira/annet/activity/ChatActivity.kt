package com.undira.annet.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.undira.annet.adapter.chat.RecyclerViewAdapter
import com.undira.annet.databinding.ActivityChatBinding
import com.undira.annet.store.UserStore
import com.undira.annet.view_model.chat.ViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var viewModel: ViewModel
    private var userOpponent: String? = null
    private var idRoom: String? = null
    private lateinit var userStore: UserStore

    companion object{
        const val USER_OPPONENT = "id_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this@ChatActivity)[ViewModel::class.java]
        userStore = UserStore(this@ChatActivity)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Message"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        userOpponent = intent.getStringExtra(USER_OPPONENT)
        if(userOpponent == null) finish()
        lifecycleScope.launch {
            val idUser = userStore.getUuid.first()
            if(userOpponent != null && idUser != null){
                checkIfTheFirstChat(idUser)
                if(idRoom != null){
                    getChat(idUser)
                }
            }
            initializeFormChat()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private suspend fun getChat(idUser: String){
        val data = viewModel.getChat(idRoom!!)
        binding.recyclerView.adapter = RecyclerViewAdapter(this@ChatActivity, data, idUser)
        binding.recyclerView.layoutManager = LinearLayoutManager(this@ChatActivity)
    }

    private suspend fun checkIfTheFirstChat(idUser: String){
        val checkGetIdChatRoom = viewModel.checkRoomExists(idUser, userOpponent!!)
        if(checkGetIdChatRoom){
            idRoom = viewModel.getIdRoom(idUser, userOpponent!!).trim('"')
        }
    }

    private fun initializeFormChat(){
        binding.sendBtn.setOnClickListener {
            binding.messageInputLayout.isErrorEnabled = false
            if(binding.messageInput.text?.trim().toString().isNotEmpty()){
                lifecycleScope.launch {
                    val userId = userStore.getUuid.first()

                    if(userId != null && userOpponent != null){
                        if(idRoom != null){
                            viewModel.addMessage(idRoom!!, userId, binding.messageInput.text.toString())
                        }else{
                            idRoom = viewModel.createNewRoom().data.trim('"')
                            viewModel.addParticipants(idRoom!!, userId)
                            viewModel.addParticipants(idRoom!!, userOpponent!!)
                            viewModel.addMessage(idRoom!!, userId, binding.messageInput.text.toString())
                        }

                        getChat(userId)
                        binding.messageInput.text?.clear()
                    }
                }
            }else{
                binding.messageInputLayout.error = "The message cannot be empty!"
            }
        }
    }
}