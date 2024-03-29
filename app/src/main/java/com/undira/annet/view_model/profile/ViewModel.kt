package com.undira.annet.view_model.profile

import androidx.lifecycle.ViewModel
import com.undira.annet.config.Provider
import com.undira.annet.model.Post
import com.undira.annet.model.PostGetAll
import com.undira.annet.model.User
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order

class ViewModel: ViewModel() {
    private val provider: SupabaseClient by lazy {
        Provider().supabase
    }

    val dataPost: ArrayList<Post> = arrayListOf(
        Post("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon Doe", "28 Jan 2023", "Hello, World!"),
        Post("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon Doe", "28 Jan 2023", "Hello, World!"),
        Post("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon Doe", "28 Jan 2023", "Hello, World!"),
        Post("https://xsgames.co/randomusers/avatar.php?g=female", "Jhon Doe", "28 Jan 2023", "Hello, World!"),
    )

    suspend fun getUser(uuid: String): User {
        return provider.from("users").select {
            filter {
                User::id eq uuid
            }
        }.decodeSingle<User>()
    }

    suspend fun getStatusProfile(uuid: String): List<PostGetAll> {
        return provider.from("posts").select(
            columns = Columns.raw("id, id_users, content, date, users(name)".trimIndent())
        ) {
            order("date", Order.DESCENDING)
            filter {
                PostGetAll::id_users eq uuid
            }
        }.decodeList<PostGetAll>()
    }
}