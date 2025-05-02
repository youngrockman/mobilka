package com.example.shoesapptest.screen.home


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoesapptest.data.remote.network.NetworkResponse
import com.example.shoesapptest.data.remote.network.NetworkResponseSneakers
import com.example.shoesapptest.data.remote.network.dto.PopularSneakersResponse
import com.example.shoesapptest.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PopularSneakersViewModel(
    private val authUseCase: AuthUseCase
) : ViewModel() {
    private val _sneakersState = MutableStateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>>(NetworkResponseSneakers.Loading)
    val sneakersState: StateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>> = _sneakersState

    private val _favoritesState = MutableStateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>>(NetworkResponseSneakers.Loading)
    val favoritesState: StateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>> = _favoritesState

    fun fetchFavorites() {
        viewModelScope.launch {
            _favoritesState.value = authUseCase.getFavorites()
        }
    }

    fun fetchSneakersByCategory(category: String) {
        viewModelScope.launch {
            _sneakersState.value = NetworkResponseSneakers.Loading
            _sneakersState.value = authUseCase.getSneakersByCategory(category)
        }
    }

    fun fetchSneakers() {
        viewModelScope.launch {
            _sneakersState.value = NetworkResponseSneakers.Loading
            try {
                val result = authUseCase.getPopularSneakers()
                when(result) {
                    is NetworkResponseSneakers.Success -> {
                        Log.d("DATA", "Received items: ${result.data}")
                        _sneakersState.value = result
                    }
                    is NetworkResponseSneakers.Error -> {
                        Log.e("ERROR", result.errorMessage)
                    }
                    NetworkResponseSneakers.Loading -> {}
                }
            } catch (e: Exception) {
                Log.e("EXCEPTION", "Error: ${e.stackTraceToString()}")
            }
        }
    }

    fun toggleFavorite(sneakerId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            when (val result = authUseCase.toggleFavorite(sneakerId, isFavorite)) {
                is NetworkResponse.Success -> {
                    fetchFavorites()
                    fetchSneakers()
                }
                is NetworkResponse.Error -> {
                    Log.e("FAVORITE", "Ошибка: ${result.errorMessage}")
                }
                NetworkResponse.Loading -> {

                }
            }
        }
    }
}

