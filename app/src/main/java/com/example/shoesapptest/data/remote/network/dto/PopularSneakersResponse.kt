package com.example.shoesapptest.data.remote.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PopularSneakersResponse(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    @SerialName("isPopular")
    val isPopular: Boolean,
    @SerialName("isFavorite")
    val isFavorite: Boolean = false,
    val quantity: Int? = null
)