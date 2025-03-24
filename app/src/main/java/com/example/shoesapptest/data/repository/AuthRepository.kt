package com.example.shoesapptest.data.repository

import com.example.shoesapptest.data.local.DataStore
import com.example.shoesapptest.data.remote.network.auth.AuthRemoteSource
import com.example.shoesapptest.data.remote.network.auth.RegistrationRequest

class AuthRepository(val dataStore: DataStore, val authRemoteSource: AuthRemoteSource) {
    suspend fun registration(registrationRequest: RegistrationRequest){
        val result = authRemoteSource.registration(registrationRequest)
        dataStore.setToken(result.second)
    }
}