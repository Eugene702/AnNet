package com.undira.annet.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.undira.annet.R
import com.undira.annet.databinding.ActivityLoginBinding
import com.undira.annet.model.UserLogin
import com.undira.annet.model.UserUUID
import com.undira.annet.store.UserStore
import com.undira.annet.view_model.login.ViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: ViewModel
    private lateinit var store: UserStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this@LoginActivity)[ViewModel::class.java]
        store = UserStore(this@LoginActivity)
        setContentView(binding.root)

        binding.registerText.setOnClickListener { startActivity(Intent(this@LoginActivity, RegisterActivity::class.java)) }
        binding.loginBtn.setOnClickListener {
            if(formValidation()){
                lifecycleScope.launch {
                    binding.loginBtn.text = resources.getString(R.string.loading)
                    binding.loginBtn.isEnabled = false

                    val userExist = viewModel.checkUser(binding.emailInput.text.toString()).countOrNull()
                    if(userExist != null && userExist.toInt() == 1){
                        try {
                            viewModel.loginUser(UserLogin(email = binding.emailInput.text.toString(), password = binding.passwordInput.text.toString()))
                            val getUuid = viewModel.getUuidUser(emailUser = binding.emailInput.text.toString()).decodeSingle<UserUUID>()
                            store.saveUser(getUuid.id)
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        }catch (e: Exception){
                            MaterialAlertDialogBuilder(this@LoginActivity)
                                .setTitle("Wrong password!")
                                .setMessage("Check your password correctly!")
                                .show()
                        }
                    }else{
                        MaterialAlertDialogBuilder(this@LoginActivity)
                            .setTitle("No users!")
                            .setMessage("User is not registered!")
                            .show()
                    }

                    binding.loginBtn.text = resources.getString(R.string.login)
                    binding.loginBtn.isEnabled = true
                }
            }
        }
    }

    private fun formValidation(): Boolean{
        var isValid: Boolean = true

        binding.layoutEmailInput.isErrorEnabled = false
        binding.layoutPasswordInput.isErrorEnabled = false

        if(binding.emailInput.text.toString().isEmpty()){
            isValid = false
            binding.layoutEmailInput.error = "The email cannot be empty!"
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(binding.emailInput.text.toString()).matches()){
            isValid = false
            binding.layoutEmailInput.error = "Incorrect email format!"
        }

        if(binding.passwordInput.text.toString().isEmpty()){
            isValid = false
            binding.layoutPasswordInput.error = "The password cannot be empty!"
        }

        if(binding.passwordInput.text.toString().length < 8 && binding.passwordInput.text.toString().isNotEmpty()){
            isValid = false
            binding.layoutPasswordInput.error = "Password must be at least 8 characters!"
        }

        return isValid
    }
}