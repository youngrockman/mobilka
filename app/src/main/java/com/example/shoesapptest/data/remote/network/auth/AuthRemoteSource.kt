package com.example.shoesapptest.data.remote.network.auth

import com.example.shoesapptest.data.remote.network.dto.AuthorizationRequest
import com.example.shoesapptest.data.remote.network.dto.AuthorizationResponse
import com.example.shoesapptest.data.remote.network.dto.PopularSneakersResponse
import com.example.shoesapptest.data.remote.network.dto.RegistrationRequest
import com.example.shoesapptest.data.remote.network.dto.RegistrationResponse
import okhttp3.ResponseBody
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthRemoteSource {
    @POST("/registration")
    suspend fun  registration(@Body registrationRequest: RegistrationRequest): RegistrationResponse

    @POST("/login")
    suspend fun login(@Body authorizationRequest: AuthorizationRequest): AuthorizationResponse

    @GET("/allSneakers")
    suspend fun popular(): List<PopularSneakersResponse>

    @GET("/sneakers/popular")
    suspend fun getPopularSneakers(): List<PopularSneakersResponse>

    @GET("/sneakers/{category}")
    suspend fun getSneakersByCategory(@Path("category") category: String): List<PopularSneakersResponse>

    @GET("/favorites")
    suspend fun getFavorites(): List<PopularSneakersResponse>

    @POST("favorites/{sneakerId}")
    suspend fun addToFavorites(@Path("sneakerId") sneakerId: Int): Response<Unit>

    @DELETE("favorites/{sneakerId}")
    suspend fun removeFromFavorites(@Path("sneakerId") sneakerId: Int): Response<Unit>

    @GET("/cart")
    suspend fun getCart(): List<PopularSneakersResponse>

    @POST("/cart/{sneakerId}")
    suspend fun addToCart(@Path("sneakerId") sneakerId: Int): Response<Unit>

    @DELETE("/cart/{sneakerId}")
    suspend fun removeFromCart(@Path("sneakerId") sneakerId: Int): Response<Unit>

    @GET("/cart/total")
    suspend fun getCartTotal(): Map<String, Double>

    @PUT("/cart/update-quantity")
    suspend fun updateCartQuantity(
        @Query("productId") productId: Int,
        @Query("quantity") quantity: Int
    ): Response<Unit>


    @DELETE("cart/remove-all/{sneakerId}")
    suspend fun removeAllFromCart(@Path("sneakerId") sneakerId: Int): Response<Unit>
}


