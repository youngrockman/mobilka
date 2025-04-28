package com.example.shoesapptest.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoesapptest.data.remote.network.NetworkResponseSneakers
import com.example.shoesapptest.data.remote.network.dto.PopularSneakersResponse
import com.example.shoesapptest.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PopularSneakersViewModel(private val authUseCase: AuthUseCase) : ViewModel() {
    private val _sneakersState = MutableStateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>>(NetworkResponseSneakers.Loading)
    val sneakersState: StateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>> = _sneakersState

    fun fetchSneakers() {
        viewModelScope.launch {
            val response = authUseCase.getSneakers()
            _sneakersState.value = response
        }
    }
}
