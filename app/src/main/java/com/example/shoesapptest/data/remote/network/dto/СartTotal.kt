package com.example.shoesapptest.data.remote.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CartTotal(
    val itemsCount: Int,
    val total: Double,
    val delivery: Double,
    val finalTotal: Double
)