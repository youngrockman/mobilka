package com.example.shoesapptest.domain.usecase

import com.example.shoesapptest.data.local.DataStore
import com.example.shoesapptest.data.remote.network.NetworkResponse
import com.example.shoesapptest.data.remote.network.NetworkResponseSneakers
import com.example.shoesapptest.data.remote.network.auth.AuthRemoteSource
import com.example.shoesapptest.data.remote.network.dto.AuthorizationRequest
import com.example.shoesapptest.data.remote.network.dto.AuthorizationResponse
import com.example.shoesapptest.data.remote.network.dto.PopularSneakersResponse
import com.example.shoesapptest.data.remote.network.dto.RegistrationRequest
import com.example.shoesapptest.data.remote.network.dto.RegistrationResponse
import com.example.shoesapptest.data.repository.AuthRepository
import com.example.shoesapptest.domain.usecase.validator.EmailValidator
import com.example.shoesapptest.domain.usecase.validator.PasswordValidator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow



class AuthUseCase(private val dataStore: DataStore, private val authRepository: AuthRepository) {
    val token: Flow<String> = dataStore.tokenFlow

    suspend fun registration(registrationRequest: RegistrationRequest): Flow<NetworkResponse<RegistrationResponse>> = flow {
        try {
            emit(NetworkResponse.Loading)
            if (!EmailValidator().validate(registrationRequest.email)) {
                emit(NetworkResponse.Error("Invalid email format"))
                return@flow
            }
            if (!PasswordValidator().validate(registrationRequest.password)) {
                emit(NetworkResponse.Error("Password must contain: 8+ chars, 1 uppercase, 1 digit, 1 special char"))
                return@flow
            }

            val result = authRepository.registration(registrationRequest)
            dataStore.setToken(result.token)
            emit(NetworkResponse.Success(result))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message ?: "Unknown Error"))

            println("Registration failed: ${e.message}")
        }
    }


    suspend fun authorization(authorizationRequest: AuthorizationRequest): Flow<NetworkResponse<AuthorizationResponse>> = flow {
        try {
            emit(NetworkResponse.Loading)
            val result = authRepository.login(authorizationRequest)
            dataStore.setToken(result.token)
            emit(NetworkResponse.Success(result))
        } catch (e: Exception) {
            emit(NetworkResponse.Error(e.message ?: "Unknown Error"))
        }
    }

    suspend fun getSneakers(): NetworkResponseSneakers<List<PopularSneakersResponse>> {
        return try {
            val result = authRepository.getSneakers()
            result
        } catch (e: Exception) {
            NetworkResponseSneakers.Error(e.message ?: "Unknown Error")
        }
    }

    suspend fun getSneakersByCategory(category: String): NetworkResponseSneakers<List<PopularSneakersResponse>> {
        return authRepository.getSneakersByCategory(category)
    }

    suspend fun getPopularSneakers(): NetworkResponseSneakers<List<PopularSneakersResponse>> {
        return authRepository.getPopularSneakers()
    }

    suspend fun getFavorites(): NetworkResponseSneakers<List<PopularSneakersResponse>> {
        return authRepository.getFavorites()
    }

    suspend fun toggleFavorite(sneakerId: Int, isFavorite: Boolean): NetworkResponse<Unit> {
        return authRepository.toggleFavorite(sneakerId, isFavorite)
    }


}




