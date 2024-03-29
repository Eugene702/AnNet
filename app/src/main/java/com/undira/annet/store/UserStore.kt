package com.undira.annet.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.undira.annet.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val PREFERENCE_NAME = "user_credential"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)
class UserStore(val context: Context) {
    private val USER_CREDENTIAL = stringPreferencesKey("UUID")
    private val USER_NAME = stringPreferencesKey("name")
    private val USER_EMAIL = stringPreferencesKey("email")


    suspend fun saveUser(data: User){
        context.dataStore.edit {
            it[USER_CREDENTIAL] = data.id
            it[USER_NAME] = data.name
            it[USER_EMAIL] = data.email
        }
    }

    val getUuid: Flow<String?> = context.dataStore.data.map { it[USER_CREDENTIAL] }
    val getName: Flow<String?> = context.dataStore.data.map { it[USER_NAME] }
    val getEmail: Flow<String?> = context.dataStore.data.map { it[USER_EMAIL] }

    suspend fun resetUser(){
        context.dataStore.edit {
            it.remove(USER_CREDENTIAL)
            it.remove(USER_NAME)
            it.remove(USER_EMAIL)
        }
    }
}