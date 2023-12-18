package com.undira.annet.config

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.FlowType
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer

class Provider {
    val supabase: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://fiblgcjezpkdvjndnyda.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImZpYmxnY2plenBrZHZqbmRueWRhIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDI2OTYyMjQsImV4cCI6MjAxODI3MjIyNH0.ImFqLkeDpvjluJ1nMkoKwmkY5TFxB8-0cXJhS24Gs2I",
    ){
        install(Auth){
            flowType = FlowType.PKCE
        }
        install(Postgrest)
        defaultSerializer = KotlinXSerializer()
    }
}