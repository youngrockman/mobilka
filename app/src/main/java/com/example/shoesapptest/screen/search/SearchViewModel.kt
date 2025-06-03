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
import com.example.shoesapptest.domain.usecase.AuthUseCase



class SneakersViewModel(
    private val authUseCase: AuthUseCase
) : ViewModel() {

    // Поисковый запрос
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    // Список продуктов (названия)
    private val _products = MutableStateFlow<List<String>>(emptyList())
    val products: StateFlow<List<String>> = _products

    // Ошибка загрузки
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // История поиска — последние 10 запросов (например)
    private val _history = MutableStateFlow<List<String>>(emptyList())
    val history: StateFlow<List<String>> = _history

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            when(val response = authUseCase.getSneakers()) {
                is NetworkResponseSneakers.Success -> {
                    val names = response.data.map { it.name }
                    _products.value = names
                    _error.value = null
                }
                is NetworkResponseSneakers.Error -> {
                    _error.value = response.errorMessage
                    _products.value = emptyList()
                }
                is NetworkResponseSneakers.Loading -> {
                    _error.value = null
                    _products.value = emptyList()
                }
            }
        }
    }

    // Обработчик изменения текста в поле поиска
    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    // Обработка клика по элементу списка (история или результат)
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun onItemClicked(item: String) {
        // Добавляем в историю, если нет дубликата
        val currentHistory = _history.value.toMutableList()
        currentHistory.remove(item)  // удаляем если уже есть, чтобы обновить порядок
        currentHistory.add(0, item)  // добавляем в начало
        // Оставляем максимум 10 последних запросов
        if (currentHistory.size > 10) {
            currentHistory.removeLast()
        }
        _history.value = currentHistory

        // Обновляем поисковый запрос
        _query.value = item
    }
}



