package com.example.shoesapptest.domain.usecase

import com.example.shoesapptest.data.local.DataStore
import com.example.shoesapptest.data.remote.network.NetworkResponse
import com.example.shoesapptest.data.remote.network.dto.AuthorizationRequest
import com.example.shoesapptest.data.remote.network.dto.AuthorizationResponse
import com.example.shoesapptest.data.remote.network.dto.RegistrationRequest
import com.example.shoesapptest.data.remote.network.dto.RegistrationResponse
import com.example.shoesapptest.data.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthUseCase(private val dataStore: DataStore, private val authRepository: AuthRepository) {
    val token: Flow<String> = dataStore.tokenFlow

    suspend fun registration(registrationRequest: RegistrationRequest): Flow<NetworkResponse<RegistrationResponse>> = flow {
        try {
            emit(NetworkResponse.Loading)
            val result = authRepository.registration(registrationRequest)
            dataStore.setToken(result.second)
            emit(NetworkResponse.Success(result))
        } catch (e: Exception) {
            e.message?.let {
                emit(NetworkResponse.Error(it))
                return@flow
            }
            emit(NetworkResponse.Error("Unknown Error"))
        }
    }

    suspend fun authorization(authorizationRequest: AuthorizationRequest): Flow<NetworkResponse<AuthorizationResponse>> = flow {
        try {
            emit(NetworkResponse.Loading)
            val result = authRepository.authorization(authorizationRequest)
            dataStore.setToken(result.token)
            emit(NetworkResponse.Success(result))
        } catch (e: Exception) {
            e.message?.let {
                emit(NetworkResponse.Error(it))
                return@flow
            }
            emit(NetworkResponse.Error("Unknown Error"))
        }
    }
}