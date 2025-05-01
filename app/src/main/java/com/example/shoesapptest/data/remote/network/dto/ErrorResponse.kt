package com.example.shoesapptest.data.remote.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val message: String,
    val errorCode: Int
)