package com.example.shoesapptest.data.remote.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationResponse (
    val first: String,
    val second: String
)