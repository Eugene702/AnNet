package com.undira.annet.view_model.search

import androidx.lifecycle.ViewModel
import com.undira.annet.config.Provider
import com.undira.annet.model.UserSearch
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order

class ViewModel: ViewModel() {
    private val provider: SupabaseClient by lazy { Provider().supabase }
    suspend fun searchQuery(query: String): List<UserSearch> {
        return provider.from("users").select(
            columns = Columns.list("id", "name")
        ) {
            filter {
                UserSearch::name ilike "%$query%"
            }
            limit(10)
            order("name", Order.ASCENDING)
            order("date", Order.DESCENDING)
        }.decodeList<UserSearch>()
    }
}
