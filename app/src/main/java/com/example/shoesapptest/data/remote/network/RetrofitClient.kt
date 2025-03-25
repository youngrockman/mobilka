package com.example.shoesapptest.data.remote.network

import com.example.shoesapptest.data.remote.network.auth.AuthRemoteSource
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


object RetrofitClient {
    private const val URL = "http://192.168.4.56:8080"
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(Json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
        .build()
    val auth by lazy {
        retrofit.create(AuthRemoteSource::class.java)
    }
}