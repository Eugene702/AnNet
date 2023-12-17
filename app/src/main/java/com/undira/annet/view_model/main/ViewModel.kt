package com.undira.annet.view_model.main

import androidx.lifecycle.ViewModel
import com.undira.annet.config.Provider
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.user.UserInfo

class ViewModel: ViewModel() {
    val supabase: SupabaseClient by lazy {
        Provider().supabase
    }

    fun getCredential(): UserInfo? {
        return supabase.auth.currentUserOrNull()
    }
}