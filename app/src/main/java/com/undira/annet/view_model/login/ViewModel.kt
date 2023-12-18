package com.undira.annet.view_model.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.undira.annet.config.Provider
import com.undira.annet.model.User
import com.undira.annet.model.UserLogin
import com.undira.annet.store.UserStore
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Count
import io.github.jan.supabase.postgrest.result.PostgrestResult

class ViewModel: ViewModel() {
    private val provider: SupabaseClient by lazy {
        Provider().supabase
    }

    suspend fun checkUser(emailUser: String): PostgrestResult {
        val data = provider.postgrest.from("users").select {
            filter {
                User::email eq emailUser
            }
            count(Count.EXACT)
        }

        return data
    }

    suspend fun getUuidUser(emailUser: String): PostgrestResult {
        val data = provider.postgrest.from("users").select {
            filter {
                User::email eq emailUser
            }
        }

        return data
    }

    suspend fun loginUser(user: UserLogin){
        val data = provider.auth.signInWith(Email){
            email = user.email
            password = user.password
        }

        return data
    }
}