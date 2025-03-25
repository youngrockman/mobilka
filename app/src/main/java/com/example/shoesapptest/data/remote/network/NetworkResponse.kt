package com.example.shoesapptest.data.remote.network

sealed class NetworkResponse<out T> {
    data class Success<out T>(val data: T) : NetworkResponse<T>()
    data object Loading : NetworkResponse<Nothing>()
    data class Error(val errorMessage: String) : NetworkResponse<Nothing>()
}