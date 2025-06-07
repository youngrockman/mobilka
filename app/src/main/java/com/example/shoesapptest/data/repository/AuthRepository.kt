package com.example.shoesapptest.data.repository

import android.util.Log
import com.example.shoesapptest.data.local.DataStore
import com.example.shoesapptest.data.remote.network.NetworkResponse
import com.example.shoesapptest.data.remote.network.NetworkResponseSneakers
import com.example.shoesapptest.data.remote.network.auth.AuthRemoteSource
import com.example.shoesapptest.data.remote.network.dto.AuthorizationRequest
import com.example.shoesapptest.data.remote.network.dto.AuthorizationResponse
import com.example.shoesapptest.data.remote.network.dto.CartTotal
import com.example.shoesapptest.data.remote.network.dto.PopularSneakersResponse
import com.example.shoesapptest.data.remote.network.dto.RegistrationRequest
import com.example.shoesapptest.data.remote.network.dto.RegistrationResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response

class AuthRepository(val authRemoteSource: AuthRemoteSource) {

    suspend fun registration(registrationRequest: RegistrationRequest): RegistrationResponse {
        return authRemoteSource.registration(registrationRequest)
    }

    suspend fun login(authorizationRequest: AuthorizationRequest): AuthorizationResponse {
        return authRemoteSource.login(authorizationRequest)
    }

    suspend fun getSneakers(): NetworkResponseSneakers<List<PopularSneakersResponse>> {
        return try {
            val result = authRemoteSource.popular()
            NetworkResponseSneakers.Success(result)
        } catch (e: Exception) {
            NetworkResponseSneakers.Error(e.message ?: "Unknown Error")
        }
    }

    suspend fun getPopularSneakers(): NetworkResponseSneakers<List<PopularSneakersResponse>> {
        return try {
            val result = authRemoteSource.getPopularSneakers()
            NetworkResponseSneakers.Success(result)
        } catch (e: Exception) {
            NetworkResponseSneakers.Error(e.message ?: "Unknown Error")
        }
    }

    suspend fun getFavorites(): NetworkResponseSneakers<List<PopularSneakersResponse>> {
        return try {
            val result = authRemoteSource.getFavorites()
            NetworkResponseSneakers.Success(result)
        } catch (e: Exception) {
            NetworkResponseSneakers.Error(e.message ?: "Unknown Error")
        }
    }

    suspend fun toggleFavorite(sneakerId: Int, isFavorite: Boolean): NetworkResponse<Unit> {
        return try {
            if (isFavorite) {
                authRemoteSource.addToFavorites(sneakerId)
            } else {
                authRemoteSource.removeFromFavorites(sneakerId)
            }
            NetworkResponse.Success(Unit)
        } catch (e: Exception) {
            NetworkResponse.Error(e.message ?: "Failed to toggle favorite")
        }
    }

    suspend fun getSneakersByCategory(category: String): NetworkResponseSneakers<List<PopularSneakersResponse>> {
        return try {
            val result = authRemoteSource.getSneakersByCategory(category)
            NetworkResponseSneakers.Success(result)
        } catch (e: Exception) {
            NetworkResponseSneakers.Error(e.message ?: "Unknown Error")
        }
    }

    suspend fun addToFavorites(sneakerId: Int): NetworkResponse<Unit> {
        return try {
            authRemoteSource.addToFavorites(sneakerId)
            NetworkResponse.Success(Unit)
        } catch (e: Exception) {
            NetworkResponse.Error(e.message ?: "Failed to add to favorites")
        }
    }

    suspend fun removeFromFavorites(sneakerId: Int): NetworkResponse<Unit> {
        return try {
            authRemoteSource.removeFromFavorites(sneakerId)
            NetworkResponse.Success(Unit)
        } catch (e: Exception) {
            NetworkResponse.Error(e.message ?: "Failed to remove from favorites")
        }
    }

    suspend fun getCart(): NetworkResponseSneakers<List<PopularSneakersResponse>> {
        return try {
            val result = authRemoteSource.getCart()
            NetworkResponseSneakers.Success(result)
        } catch (e: Exception) {
            NetworkResponseSneakers.Error(e.message ?: "Failed to get cart")
        }
    }

    suspend fun addToCart(sneakerId: Int): NetworkResponse<Unit> {
        return try {
            authRemoteSource.addToCart(sneakerId)
            NetworkResponse.Success(Unit)
        } catch (e: Exception) {
            NetworkResponse.Error(e.message ?: "Failed to add to cart")
        }
    }

    suspend fun removeFromCart(sneakerId: Int): NetworkResponse<Unit> {
        return try {
            authRemoteSource.removeFromCart(sneakerId)
            NetworkResponse.Success(Unit)
        } catch (e: Exception) {
            NetworkResponse.Error(e.message ?: "Failed to remove from cart")
        }
    }

    suspend fun getCartTotal(): NetworkResponse<CartTotal> {
        return try {
            val result = authRemoteSource.getCartTotal()
            NetworkResponse.Success(CartTotal(
                itemsCount = result["itemsCount"]?.toInt() ?: 0,
                total = result["total"] ?: 0.0,
                delivery = result["delivery"] ?: 0.0,
                finalTotal = result["finalTotal"] ?: 0.0
            ))
        } catch (e: Exception) {
            NetworkResponse.Error(e.message ?: "Failed to get cart total")
        }
    }
}









