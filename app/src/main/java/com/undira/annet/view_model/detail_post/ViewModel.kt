package com.undira.annet.view_model.detail_post

import androidx.lifecycle.ViewModel
import com.undira.annet.config.Provider
import com.undira.annet.model.Comment
import com.undira.annet.model.CommentInput
import com.undira.annet.model.CommentList
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import io.github.jan.supabase.postgrest.result.PostgrestResult

class ViewModel: ViewModel() {
    private val provider: SupabaseClient by lazy {
        Provider().supabase
    }

    suspend fun addComment(data: CommentInput): PostgrestResult {
        return provider.from("comment").insert(data)
    }

    suspend fun getAllComment(idPost: String): List<CommentList> {
        return provider.from("comment").select(
            columns = Columns.raw("*, users(name)".trimIndent())
        ){
            filter {
                CommentList::id_post eq idPost
            }
            order("date", Order.DESCENDING)
        }.decodeList<CommentList>()
    }
}
