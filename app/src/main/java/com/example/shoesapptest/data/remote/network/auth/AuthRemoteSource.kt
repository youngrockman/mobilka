package com.example.shoesapptest.data.remote.network.auth

import com.example.shoesapptest.data.remote.network.dto.RegistrationRequest
import com.example.shoesapptest.data.remote.network.dto.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRemoteSource {
    @POST("/registration")
    suspend fun  registration(@Body registrationRequest: RegistrationRequest): RegistrationResponse
}