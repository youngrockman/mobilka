package com.example.shoesapptest.data.remote.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateCartQuantityRequest(
    val productId: Int,
    val quantity: Int
)