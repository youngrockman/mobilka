package com.example.shoesapptest.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoesapptest.data.remote.network.NetworkResponseSneakers
import com.example.shoesapptest.data.remote.network.NetworkResponseSneakers.Error
import com.example.shoesapptest.data.remote.network.NetworkResponseSneakers.Loading
import com.example.shoesapptest.data.remote.network.NetworkResponseSneakers.Success
import com.example.shoesapptest.data.remote.network.dto.PopularSneakersResponse
import com.example.shoesapptest.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PopularSneakersViewModel(
    private val authUseCase: AuthUseCase
) : ViewModel() {


    private val _sneakersState =
        MutableStateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>>(Loading)
    val sneakersState: StateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>> = _sneakersState


    private val _favoritesState =
        MutableStateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>>(Loading)
    val favoritesState: StateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>> = _favoritesState


    private val favoriteIds = mutableSetOf<Int>()


    fun fetchSneakers() {
        viewModelScope.launch {
            _sneakersState.value = Loading
            try {
                val result = authUseCase.getPopularSneakers()
                when (result) {
                    is Success -> {
                        val mergedList = result.data.map { sneaker ->
                            sneaker.copy(isFavorite = (sneaker.id in favoriteIds))
                        }
                        _sneakersState.value = Success(mergedList)
                    }
                    is Error -> {
                        _sneakersState.value = Error(result.errorMessage)
                    }
                    Loading -> {

                    }
                }
            } catch (e: Exception) {
                Log.e("PopularVM", "Error fetchSneakers: ${e.localizedMessage}")
                _sneakersState.value = Error(e.message ?: "Unknown error")
            }
        }
    }

    fun fetchSneakersByCategory(category: String) {
        viewModelScope.launch {
            _sneakersState.value = Loading
            try {
                val result = authUseCase.getSneakersByCategory(category)
                when (result) {
                    is Success -> {
                        val mergedList = result.data.map { sneaker ->
                            sneaker.copy(isFavorite = (sneaker.id in favoriteIds))
                        }
                        _sneakersState.value = Success(mergedList)
                    }
                    is Error -> {
                        _sneakersState.value = Error(result.errorMessage)
                    }
                    Loading -> {

                    }
                }
            } catch (e: Exception) {
                Log.e("PopularVM", "Error fetchSneakersByCategory: ${e.localizedMessage}")
                _sneakersState.value = Error(e.message ?: "Unknown error")
            }
        }
    }


    fun fetchFavorites() {
        viewModelScope.launch {
            _favoritesState.value = Loading
            try {
                val result = authUseCase.getFavorites()
                when (result) {
                    is Success -> {
                        val favList = result.data.map { sneaker ->
                            sneaker.copy(isFavorite = true)
                        }
                        // Обновляем локальный кэш ID
                        favoriteIds.clear()
                        favoriteIds.addAll(favList.map { it.id })
                        _favoritesState.value = Success(favList)
                    }
                    is Error -> {
                        _favoritesState.value = Error(result.errorMessage)
                    }
                    Loading -> {
                        // не должно зайти
                    }
                }
            } catch (e: Exception) {
                Log.e("PopularVM", "Error fetchFavorites: ${e.localizedMessage}")
                _favoritesState.value = Error(e.message ?: "Unknown error")
            }
        }
    }

    fun toggleFavorite(sneakerId: Int, wasFavoriteBeforeClick: Boolean) {
        viewModelScope.launch {

            val newFavorite = !wasFavoriteBeforeClick


            updateFavoriteStatusOnBackend(sneakerId, newFavorite)


            if (newFavorite) favoriteIds.add(sneakerId) else favoriteIds.remove(sneakerId)


            (_sneakersState.value as? Success<List<PopularSneakersResponse>>)?.let { successState ->
                val updatedSneakers = successState.data.map { sneaker ->
                    if (sneaker.id == sneakerId) {
                        sneaker.copy(isFavorite = newFavorite)
                    } else {
                        sneaker
                    }
                }
                _sneakersState.value = Success(updatedSneakers)
            }

            (_favoritesState.value as? Success<List<PopularSneakersResponse>>)?.let { successState ->
                if (newFavorite) {
                    val added = (_sneakersState.value as? Success<List<PopularSneakersResponse>>)
                        ?.data
                        ?.find { it.id == sneakerId }
                    if (added != null) {
                        val updatedFavorites = successState.data + added
                        _favoritesState.value = Success(updatedFavorites)
                    }
                } else {

                    val updatedFavorites = successState.data.filter { it.id != sneakerId }
                    _favoritesState.value = Success(updatedFavorites)
                }
            }
        }
    }


    private suspend fun updateFavoriteStatusOnBackend(sneakerId: Int, favorite: Boolean) {
        if (favorite) {
            authUseCase.addToFavorites(sneakerId)
        } else {
            authUseCase.removeFromFavorites(sneakerId)
        }
    }
}
