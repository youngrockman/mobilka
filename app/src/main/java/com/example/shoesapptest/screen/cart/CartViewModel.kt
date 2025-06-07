package com.example.shoesapptest.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoesapptest.data.remote.network.NetworkResponse
import com.example.shoesapptest.data.remote.network.NetworkResponseSneakers
import com.example.shoesapptest.data.remote.network.dto.CartTotal
import com.example.shoesapptest.data.remote.network.dto.PopularSneakersResponse
import com.example.shoesapptest.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val authUseCase: AuthUseCase) : ViewModel() {
    private val _cartState = MutableStateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>>(NetworkResponseSneakers.Loading)
    val cartState: StateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>> = _cartState.asStateFlow()

    private val _totalState = MutableStateFlow<NetworkResponse<CartTotal>>(NetworkResponse.Loading)
    val totalState: StateFlow<NetworkResponse<CartTotal>> = _totalState.asStateFlow()

    init {
        fetchCart()
    }

    fun fetchCart() {
        viewModelScope.launch {
            _cartState.value = NetworkResponseSneakers.Loading
            _totalState.value = NetworkResponse.Loading

            _cartState.value = authUseCase.getCart()
            _totalState.value = authUseCase.getCartTotal()
        }
    }

    fun removeFromCart(sneakerId: Int) {
        viewModelScope.launch {
            when (authUseCase.removeFromCart(sneakerId)) {
                is NetworkResponse.Success -> fetchCart()
                is NetworkResponse.Error -> Unit
                NetworkResponse.Loading -> Unit
            }
        }
    }

    fun addToCart(sneakerId: Int) {
        viewModelScope.launch {
            when (authUseCase.addToCart(sneakerId)) {
                is NetworkResponse.Success -> fetchCart()
                is NetworkResponse.Error -> Unit
                NetworkResponse.Loading -> Unit
            }
        }
    }

}