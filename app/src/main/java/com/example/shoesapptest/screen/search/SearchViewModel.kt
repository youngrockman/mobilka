package com.example.shoesapptest.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoesapptest.data.local.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _products = MutableStateFlow<List<String>>(emptyList())
    val products: StateFlow<List<String>> = _products.asStateFlow()

    private val _history = MutableStateFlow<List<String>>(emptyList())
    val history: StateFlow<List<String>> = _history.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreManager.searchHistory.collect { list ->
                _history.value = list.reversed()
            }
        }


        loadProductsFromServer()
    }

    private fun loadProductsFromServer() {

        _products.value = listOf(
            "Nike Air Max", "Adidas Ultraboost", "Puma Running",
            "Reebok Classic", "New Balance 574", "Nike Top Shoes", "Snakers Nike Shoes"
        )
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun onItemClicked(item: String) {
        _query.value = item
        viewModelScope.launch {
            dataStoreManager.saveQuery(item)
        }
    }
}
