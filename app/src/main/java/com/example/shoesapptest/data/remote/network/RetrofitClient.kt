package com.example.shoesapptest.data.remote.network

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.shoesapptest.data.local.DataStore
import com.example.shoesapptest.data.remote.network.auth.AuthRemoteSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class AuthInterceptor(private val dataStore: DataStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { dataStore.tokenFlow.first() }
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        Log.d("AUTH", "Token: $token")
        return chain.proceed(request)
    }
}


class RetrofitClient(
    private val authInterceptor: AuthInterceptor
) {
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.0.107:8080/")
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val authService: AuthRemoteSource by lazy {
        retrofit.create(AuthRemoteSource::class.java)
    }
}