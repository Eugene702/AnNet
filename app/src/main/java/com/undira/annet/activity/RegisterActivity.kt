package com.undira.annet.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.undira.annet.R
import com.undira.annet.databinding.ActivityRegisterBinding
import com.undira.annet.model.UserInsert
import com.undira.annet.view_model.register.ViewModel
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this@RegisterActivity)[ViewModel::class.java]
        setContentView(binding.root)

        binding.signinText.setOnClickListener {
            finish()
        }

        binding.registerBtn.setOnClickListener {
            if(formValidateInput()){
                var isError: Boolean
                var titleDialog: String
                var messagesDialog: String

                lifecycleScope.launch {
                    binding.registerBtn.text = resources.getString(R.string.loading)
                    binding.registerBtn.isEnabled = false

                    try {
                        viewModel.registerUser(emailUser = binding.emailInput.text.toString(), passwordUser = binding.passwordInput.text.toString())
                        val checkUser: Long? = viewModel.checkUser(emailUser = binding.emailInput.text.toString()).countOrNull()

                        if(checkUser?.toInt() == 0){
                            isError = false
                            viewModel.insertUser(UserInsert(email = binding.emailInput.text.toString(), name = binding.nameInput.text.toString()))
                            titleDialog = "Account registered successfully!"
                            messagesDialog = "Your account has been successfully registered, please check your email to activate your account!"
                        }else{
                            isError = true
                            titleDialog = "Account registered!"
                            messagesDialog = "Your email has been registered!"
                        }
                    }catch (_: Exception){
                        isError = true
                        titleDialog = "Server error!"
                        messagesDialog = "There's a server error!"
                    }

                    binding.registerBtn.text = resources.getString(R.string.register)
                    binding.registerBtn.isEnabled = true

                    MaterialAlertDialogBuilder(this@RegisterActivity)
                        .setTitle(titleDialog)
                        .setMessage(messagesDialog)
                        .setPositiveButton(
                            "Ok!"
                        ) { dialog, _ ->
                            if (!isError) {
                                finish()
                            } else {
                                dialog.dismiss()
                            }
                        }
                        .show()
                }
            }
        }
    }

    private fun formValidateInput(): Boolean{
        var isValid = true

        binding.layoutNameInput.isErrorEnabled = false
        binding.layoutEmailInput.isErrorEnabled = false
        binding.layoutPasswordInput.isErrorEnabled = false
        binding.layoutRepeatPasswordInput.isErrorEnabled = false

        if(binding.nameInput.text.toString().isEmpty()){
            isValid = false
            binding.layoutNameInput.error = "The name cannot be empty!"
        }

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

        if(binding.repeatPasswordInput.text.toString().isEmpty()){
            isValid = false
            binding.layoutRepeatPasswordInput.error = "The repeat password cannot be empty!"
        }

        if(binding.repeatPasswordInput.text.toString().length < 8  && binding.passwordInput.text.toString().isNotEmpty()){
            isValid = false
            binding.layoutRepeatPasswordInput.error = "Repeat password must be at least 8 characters!"
        }

        if(binding.passwordInput.text.toString() != binding.repeatPasswordInput.text.toString()){
            isValid = false
            binding.layoutRepeatPasswordInput.error = "Password must be the same!"
        }

        return isValid
    }
}