package com.undira.annet.view_model.register

import android.util.Log
import androidx.lifecycle.ViewModel
import com.undira.annet.config.Provider
import com.undira.annet.model.User
import com.undira.annet.model.UserInsert
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Count
import io.github.jan.supabase.postgrest.result.PostgrestResult

class ViewModel: ViewModel() {
    private val supabase: SupabaseClient by lazy {
        Provider().supabase
    }

    suspend fun checkUser(emailUser: String): PostgrestResult {
        val user = supabase.from("users").select(){
            filter {
                User::email eq emailUser
            }
            count(Count.EXACT)
        }

        return user
    }

    suspend fun registerUser(emailUser: String, passwordUser: String): Email.Result? {
        val register = supabase.auth.signUpWith(Email){
            email = emailUser
            password = passwordUser
        }
        Log.d("Results", register.toString())
        return register
    }

    suspend fun insertUser(user: UserInsert): PostgrestResult {
        return supabase.from("users").insert<UserInsert>(user)
    }
}