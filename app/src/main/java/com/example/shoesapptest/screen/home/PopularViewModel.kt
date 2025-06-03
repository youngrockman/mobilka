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

    fun toggleFavorite(sneakerId: Int, isCurrentlyFavorite: Boolean) {
        viewModelScope.launch {

            val newFavorite = !isCurrentlyFavorite


            updateFavoriteStatus(sneakerId, newFavorite)


            (_sneakersState.value as? NetworkResponseSneakers.Success)?.let { successState ->
                val updatedSneakers = successState.data.map { sneaker ->
                    if (sneaker.id == sneakerId) {
                        sneaker.copy(isFavorite = newFavorite)
                    } else {
                        sneaker
                    }
                }
                _sneakersState.value = NetworkResponseSneakers.Success(updatedSneakers)
            }


            (_favoritesState.value as? NetworkResponseSneakers.Success)?.let { successState ->
                if (newFavorite) {
                    val addedSneaker = (_sneakersState.value as? NetworkResponseSneakers.Success)
                        ?.data
                        ?.find { it.id == sneakerId }

                    if (addedSneaker != null) {
                        val updatedFavorites = successState.data + addedSneaker
                        _favoritesState.value = NetworkResponseSneakers.Success(updatedFavorites)
                    }
                } else {
                    val updatedFavorites = successState.data.filter { it.id != sneakerId }
                    _favoritesState.value = NetworkResponseSneakers.Success(updatedFavorites)
                }
            }
        }
    }

    private suspend fun updateFavoriteStatus(sneakerId: Int, favorite: Boolean) {
        if (favorite) {
            authUseCase.addToFavorites(sneakerId)
        } else {
            authUseCase.removeFromFavorites(sneakerId)
        }
    }

}

