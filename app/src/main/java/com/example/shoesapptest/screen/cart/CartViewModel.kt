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
    private val _cartState =
        MutableStateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>>(
            NetworkResponseSneakers.Loading
        )
    val cartState: StateFlow<NetworkResponseSneakers<List<PopularSneakersResponse>>> =
        _cartState.asStateFlow()

    private val _totalState = MutableStateFlow<NetworkResponse<CartTotal>>(NetworkResponse.Loading)
    val totalState: StateFlow<NetworkResponse<CartTotal>> = _totalState.asStateFlow()

    init {
        fetchCart()
    }

    fun fetchCart() {
        viewModelScope.launch {
            _cartState.value = NetworkResponseSneakers.Loading
            _totalState.value = NetworkResponse.Loading

            val cartResult = authUseCase.getCart()
            _cartState.value = cartResult

            if (cartResult is NetworkResponseSneakers.Success) {
                calculateTotal(cartResult.data)
            }
        }
    }


    fun removeFromCart(sneakerId: Int) {
        viewModelScope.launch {
            val result = authUseCase.removeFromCart(sneakerId)
            if (result is NetworkResponse.Success) {
                fetchCart()
            }
        }
    }


    fun addToCart(sneakerId: Int) {
        viewModelScope.launch {
            val result = authUseCase.addToCart(sneakerId)
            if (result is NetworkResponse.Success) {
                fetchCart()
            }
        }
    }



    fun calculateTotal(items: List<PopularSneakersResponse>) {
        val itemsCount = items.sumOf { it.quantity ?: 1 }
        val total = items.sumOf { it.price * (it.quantity ?: 1).toDouble() }
        val delivery = 500.0
        val finalTotal = total + delivery

        _totalState.value = NetworkResponse.Success(
            CartTotal(itemsCount, total, delivery, finalTotal)
        )
    }

    fun updateQuantity(sneakerId: Int, newQuantity: Int) {
        viewModelScope.launch {
            authUseCase.updateCartQuantity(sneakerId, newQuantity)
            fetchCart()
        }
    }




}
