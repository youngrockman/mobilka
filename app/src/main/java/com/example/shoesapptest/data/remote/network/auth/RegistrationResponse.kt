package com.example.shoesapptest.data.remote.network.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationResponse (
    val first: String,
    val second: String
)