package com.undira.annet.view_model.register

import android.util.Log
import androidx.lifecycle.ViewModel
import com.undira.annet.config.Provider
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email

class ViewModel: ViewModel() {
    private val supabase: SupabaseClient by lazy {
        Provider().supabase
    }

    suspend fun registerUser(emailUser: String, passwordUser: String){
        val register = supabase.auth.signUpWith(Email){
            email = emailUser
            password = passwordUser
        }

        Log.d("Register result", register.toString())
    }
}