package com.undira.annet.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREFERENCE_NAME = "user_credential"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)
class UserStore(val context: Context) {
    private val USER_CREDENTIAL = stringPreferencesKey("UUID")

    suspend fun saveUser(uuid: String){
        context.dataStore.edit {
            it[USER_CREDENTIAL] = uuid
        }
    }

    val readUserCredential: Flow<String?> = context.dataStore.data
        .map {
            it[USER_CREDENTIAL]
        }
}