package com.example.shoesapptest.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {
    private val SEARCH_HISTORY_KEY = stringPreferencesKey("search_history")

    suspend fun saveSearchHistory(history: List<String>) {
        context.dataStore.edit { preferences ->
            preferences[SEARCH_HISTORY_KEY] = history.joinToString("|")
        }
    }

    fun getSearchHistory(): Flow<List<String>> = context.dataStore.data
        .map { preferences ->
            preferences[SEARCH_HISTORY_KEY]?.split("|")?.filter { it.isNotBlank() } ?: emptyList()
        }
}