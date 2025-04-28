package com.example.shoesapptest.data.remote.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class PopularSneakersResponse(
    val id: Int,
    var productName: String,
    var cost: String,
    var count: Int,
    var photo: String,
    var description: String
)