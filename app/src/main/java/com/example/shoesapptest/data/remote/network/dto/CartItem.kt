package com.example.shoesapptest.data.remote.network.dto

import kotlinx.serialization.Serializable


@Serializable
data class CartItem(
    val sneaker: PopularSneakersResponse,
    var quantity: Int
)
