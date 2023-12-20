package com.undira.annet.view_model.detail_post

import androidx.lifecycle.ViewModel
import com.undira.annet.config.Provider
import com.undira.annet.model.Comment
import com.undira.annet.model.CommentInput
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.result.PostgrestResult

class ViewModel: ViewModel() {
    private val provider: SupabaseClient by lazy {
        Provider().supabase
    }

    val data: ArrayList<Comment> = arrayListOf(
        Comment("1", "https://xsgames.co/randomusers/avatar.php?g=female", "28 Jan 2023", "Jhon Doe", "Helloo, World!"),
        Comment("1", "https://xsgames.co/randomusers/avatar.php?g=female", "28 Jan 2023", "Jhon Doe", "Helloo, World!"),
        Comment("1", "https://xsgames.co/randomusers/avatar.php?g=female", "28 Jan 2023", "Jhon Doe", "Helloo, World!"),
        Comment("1", "https://xsgames.co/randomusers/avatar.php?g=female", "28 Jan 2023", "Jhon Doe", "Helloo, World!"),
        Comment("1", "https://xsgames.co/randomusers/avatar.php?g=female", "28 Jan 2023", "Jhon Doe", "Helloo, World!"),
        Comment("1", "https://xsgames.co/randomusers/avatar.php?g=female", "28 Jan 2023", "Jhon Doe", "Helloo, World!"),
        Comment("1", "https://xsgames.co/randomusers/avatar.php?g=female", "28 Jan 2023", "Jhon Doe", "Helloo, World!"),
        Comment("1", "https://xsgames.co/randomusers/avatar.php?g=female", "28 Jan 2023", "Jhon Doe", "Helloo, World!"),
    )

    suspend fun addComment(data: CommentInput): PostgrestResult {
        return provider.from("comment").insert(data)
    }
}
