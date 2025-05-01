package com.example.shoesapptest.data.remote.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationResponse(
    val token: String,
    val userId: Int,
    val userName: String,
    val email: String
)