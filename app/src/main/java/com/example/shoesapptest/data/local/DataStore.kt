package com.example.shoesapptest.data.local


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class DataStore(private val context: Context) {
    val TOKEN_KEY = stringPreferencesKey("token_key")
    val tokenFlow : Flow<String> = context.dataStore.data.map { pref ->
        pref[TOKEN_KEY] ?: ""
    }
    suspend fun setToken(token: String){
        context.dataStore.edit { pref ->
            pref[TOKEN_KEY] = token
        }
    }
}