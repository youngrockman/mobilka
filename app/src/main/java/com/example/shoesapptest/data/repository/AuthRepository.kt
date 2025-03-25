package com.example.shoesapptest.data.repository

import com.example.shoesapptest.data.local.DataStore
import com.example.shoesapptest.data.remote.network.NetworkResponse
import com.example.shoesapptest.data.remote.network.auth.AuthRemoteSource
import com.example.shoesapptest.data.remote.network.dto.RegistrationRequest
import com.example.shoesapptest.data.remote.network.dto.RegistrationResponse

class AuthRepository(val authRemoteSource: AuthRemoteSource) {
    suspend fun registration(registrationRequest: RegistrationRequest): RegistrationResponse {
        return authRemoteSource.registration(registrationRequest)
    }
}