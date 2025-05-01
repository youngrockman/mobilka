package com.example.shoesapptest.data.remote.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class PopularSneakersResponse(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String,
    val isPopular: Boolean,
    val isFavorite: Boolean = false
)