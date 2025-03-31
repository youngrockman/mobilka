package com.example.shoesapptest.data.remote.network.dto

data class AuthorizationResponse (
    val token: String,
    val userId: String,
    val userName: String,
    val email: String,
    val expiresIn: Long
)