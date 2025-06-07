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



class SneakersViewModel(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _products = MutableStateFlow<List<String>>(emptyList())
    val products: StateFlow<List<String>> = _products.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _history = MutableStateFlow<List<String>>(emptyList())
    val history: StateFlow<List<String>> = _history.asStateFlow()

    private val _sneakersResponse = MutableStateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>>(NetworkResponseSneakers.Loading)
    val sneakersResponse: StateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>> = _sneakersResponse.asStateFlow()

    private val _searchResults = MutableStateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>>(NetworkResponseSneakers.Loading)
    val searchResults: StateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>> = _searchResults.asStateFlow()


    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _sneakersResponse.value = NetworkResponseSneakers.Loading
            when (val response = authUseCase.getSneakers()) {
                is NetworkResponseSneakers.Success -> {
                    _sneakersResponse.value = response
                    _products.value = response.data.map { it.name }
                    _error.value = null
                }
                is NetworkResponseSneakers.Error -> {
                    _sneakersResponse.value = response
                    _error.value = response.errorMessage
                    _products.value = emptyList()
                }
                NetworkResponseSneakers.Loading -> {
                    _sneakersResponse.value = NetworkResponseSneakers.Loading
                    _error.value = null
                    _products.value = emptyList()
                }
            }
        }
    }

    fun onQueryChanged(new: String) {
        _query.value = new
        if (new.isNotBlank()) fetch()
    }

    private fun fetch() {
        viewModelScope.launch {
            _searchResults.value = NetworkResponseSneakers.Loading
            _searchResults.value = authUseCase.searchSneakers(_query.value)
        }
    }


    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    private fun fetchFilteredSneakers(query: String) {
        viewModelScope.launch {
            _sneakersResponse.value = NetworkResponseSneakers.Loading
            val response = authUseCase.searchSneakers(query)
            _sneakersResponse.value = response
            if (response is NetworkResponseSneakers.Success) {
                updateHistory(query)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    private fun updateHistory(query: String) {
        val currentHistory = _history.value.toMutableList()
        currentHistory.remove(query)
        currentHistory.add(0, query)
        if (currentHistory.size > 10) currentHistory.removeLast()
        _history.value = currentHistory
    }


    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun onItemClicked(item: String) {
        val currentHistory = _history.value.toMutableList()
        currentHistory.remove(item)
        currentHistory.add(0, item)
        if (currentHistory.size > 10) {
            currentHistory.removeLast()
        }
        _history.value = currentHistory
        _query.value = item
    }

    fun addToCart(sneakerId: Int) {
        viewModelScope.launch {
            authUseCase.addToCart(sneakerId)
        }
    }
}

