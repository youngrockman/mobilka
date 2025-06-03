package com.example.shoesapptest.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {
    private val SEARCH_HISTORY_KEY = stringSetPreferencesKey("search_history")

    val searchHistory: Flow<List<String>> = context.searchDataStore.data
        .map { prefs ->
            prefs[SEARCH_HISTORY_KEY]?.toList() ?: emptyList()
        }

    suspend fun saveQuery(query: String) {
        context.searchDataStore.edit { prefs ->
            val current = prefs[SEARCH_HISTORY_KEY]?.toMutableSet() ?: mutableSetOf()
            current.remove(query)
            current.add(query)
            if (current.size > 10) {
                current.remove(current.first())
            }
            prefs[SEARCH_HISTORY_KEY] = current
        }
    }
}
