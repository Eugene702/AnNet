package com.undira.annet.view_model.main.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import com.undira.annet.config.Provider
import com.undira.annet.model.GetInbox
import com.undira.annet.model.Inbox
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.rpc

class ViewModel: ViewModel() {
    private val provider: SupabaseClient by lazy { Provider().supabase }

    suspend fun getInbox(idUser: String): List<Inbox> {
        val data = provider.postgrest.rpc("get_last_message_each_user", GetInbox(idUser))
        return data.decodeList<Inbox>()
    }
}