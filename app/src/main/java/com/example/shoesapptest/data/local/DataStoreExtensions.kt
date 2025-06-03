package com.example.shoesapptest.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

import androidx.datastore.preferences.preferencesDataStore

val Context.searchDataStore: DataStore<Preferences> by preferencesDataStore(name = "search_prefs")