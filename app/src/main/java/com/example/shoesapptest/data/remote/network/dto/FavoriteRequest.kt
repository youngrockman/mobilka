package com.example.shoesapptest.data.remote.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteRequest(
    val sneakerId: Int
)