package com.example.shoesapptest.data.remote.network


sealed class NetworkResponseSneakers <out T>{
    data class Success<T>(val data: T): NetworkResponseSneakers<T>()
    data object Loading: NetworkResponseSneakers<Nothing>()
    data class Error(val errorMessage: String): NetworkResponseSneakers<Nothing>()
}