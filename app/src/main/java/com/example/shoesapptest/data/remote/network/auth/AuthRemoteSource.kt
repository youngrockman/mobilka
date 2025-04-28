package com.example.shoesapptest.data.remote.network.auth

import com.example.shoesapptest.data.remote.network.dto.AuthorizationRequest
import com.example.shoesapptest.data.remote.network.dto.AuthorizationResponse
import com.example.shoesapptest.data.remote.network.dto.PopularSneakersResponse
import com.example.shoesapptest.data.remote.network.dto.RegistrationRequest
import com.example.shoesapptest.data.remote.network.dto.RegistrationResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthRemoteSource {
    @POST("/registration")
    suspend fun  registration(@Body registrationRequest: RegistrationRequest): RegistrationResponse

    @POST("/authorization")
    suspend fun authorization(@Body authorizationRequest: AuthorizationRequest): AuthorizationResponse

    @GET("/allSneakers")
    suspend fun popular(): List<PopularSneakersResponse>

}
