package com.example.shoesapptest.screen.search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoesapptest.data.local.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.shoesapptest.data.remote.network.NetworkResponseSneakers
import com.example.shoesapptest.data.remote.network.dto.PopularSneakersResponse
import com.example.shoesapptest.domain.usecase.AuthUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay


class SneakersViewModel(
    private val authUseCase: AuthUseCase,
    private val dataStore: DataStoreManager
) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _history = MutableStateFlow<List<String>>(emptyList())
    val history: StateFlow<List<String>> = _history.asStateFlow()

    private val _searchResults = MutableStateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>>(NetworkResponseSneakers.Loading)
    val searchResults: StateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>> = _searchResults.asStateFlow()

    private var searchJob: Job? = null
    private var saveHistoryJob: Job? = null

    init {
        loadHistory()
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
        searchJob?.cancel()

        if (newQuery.isNotEmpty()) {
            searchJob = viewModelScope.launch {
                delay(300)
                fetchSearchResults(newQuery)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    private suspend fun fetchSearchResults(query: String) {
        _searchResults.value = NetworkResponseSneakers.Loading
        val response = authUseCase.searchSneakers(query)
        _searchResults.value = response

        if (response is NetworkResponseSneakers.Success && response.data.isNotEmpty()) {
            updateHistory(query)
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    private fun updateHistory(query: String) {
        saveHistoryJob?.cancel()
        saveHistoryJob = viewModelScope.launch {
            delay(1000)
            val current = _history.value.toMutableList()
            current.remove(query)
            current.add(0, query)
            if (current.size > 10) current.removeLast()
            _history.value = current
            dataStore.saveSearchHistory(current)
        }
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    suspend fun onItemClicked(item: String) {
        _query.value = item
        fetchSearchResults(item)
    }

    fun addToCart(sneakerId: Int) {
        viewModelScope.launch {
            authUseCase.addToCart(sneakerId)
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            dataStore.getSearchHistory().collect { history ->
                _history.value = history
            }
        }
    }
}



