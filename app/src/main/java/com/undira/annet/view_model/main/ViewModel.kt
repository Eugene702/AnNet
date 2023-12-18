package com.undira.annet.view_model.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.undira.annet.config.Provider
import io.github.jan.supabase.SupabaseClient

class ViewModel: ViewModel() {
    val supabase: SupabaseClient by lazy {
        Provider().supabase
    }
}