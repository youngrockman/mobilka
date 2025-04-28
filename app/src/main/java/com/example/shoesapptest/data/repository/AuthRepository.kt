package com.example.shoesapptest.data.repository

import com.example.shoesapptest.data.local.DataStore
import com.example.shoesapptest.data.remote.network.NetworkResponse
import com.example.shoesapptest.data.remote.network.NetworkResponseSneakers
import com.example.shoesapptest.data.remote.network.auth.AuthRemoteSource
import com.example.shoesapptest.data.remote.network.dto.AuthorizationRequest
import com.example.shoesapptest.data.remote.network.dto.AuthorizationResponse
import com.example.shoesapptest.data.remote.network.dto.PopularSneakersResponse
import com.example.shoesapptest.data.remote.network.dto.RegistrationRequest
import com.example.shoesapptest.data.remote.network.dto.RegistrationResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepository(val authRemoteSource: AuthRemoteSource) {

    suspend fun registration(registrationRequest: RegistrationRequest): RegistrationResponse {
        return authRemoteSource.registration(registrationRequest)
    }

    suspend fun authorization(authorizationRequest: AuthorizationRequest): AuthorizationResponse {
        return authRemoteSource.authorization(authorizationRequest)
    }

    suspend fun getSneakers(): NetworkResponseSneakers<List<PopularSneakersResponse>> {
        return try {
            val result = authRemoteSource.popular()
            NetworkResponseSneakers.Success(result)
        } catch (e: Exception) {
            NetworkResponseSneakers.Error(e.message ?: "Unknown Error")
        }
    }
}
