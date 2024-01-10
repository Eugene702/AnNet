package com.undira.annet.view_model.main.home

import androidx.lifecycle.ViewModel
import com.undira.annet.config.Provider
import com.undira.annet.model.Post
import com.undira.annet.model.PostGetAll
import com.undira.annet.model.PostInsert
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.result.PostgrestResult

class HomeViewModel: ViewModel() {
    private val provider: SupabaseClient by lazy {
        Provider().supabase
    }

    suspend fun addPost(data: PostInsert): PostgrestResult {
        return provider.from("posts").insert<PostInsert>(data)
    }

    suspend fun getData(): List<PostGetAll> {
        return provider.from("posts").select(
            columns = Columns.raw("id, id_users, content, date, users(name)".trimIndent())
        ){
            order("date", Order.DESCENDING)
        }.decodeList<PostGetAll>()
    }
}