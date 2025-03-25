package com.example.shoesapptest.screen.regscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoesapptest.data.remote.network.NetworkResponse
import com.example.shoesapptest.data.remote.network.dto.RegistrationRequest
import com.example.shoesapptest.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class RegistrationViewModel(val authUseCase: AuthUseCase) : ViewModel() {
    val registrationScreenState = mutableStateOf(RegistrationScreenState())

    fun setEmail(email: String) {
        registrationScreenState.value = registrationScreenState.value.copy(
            email = email,
            emailHasError = !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        )
    }

    fun setPassword(password: String) {
        registrationScreenState.value = registrationScreenState.value.copy(password = password)
    }

    fun setName(name: String) {
        registrationScreenState.value = registrationScreenState.value.copy(name = name)
    }

    fun setErrorMessage(message: String?) {
        registrationScreenState.value = registrationScreenState.value.copy(errorMessage = message)
    }

    private fun setLoading(isLoading: Boolean) {
        registrationScreenState.value = registrationScreenState.value.copy(isLoading = isLoading)
    }

    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val registrationRequest = RegistrationRequest(
                email = registrationScreenState.value.email,
                userName = registrationScreenState.value.name,
                password = registrationScreenState.value.password
            )

            authUseCase.registration(registrationRequest).collect { response ->
                when(response) {
                    is NetworkResponse.Error -> {
                        setLoading(false)
                        setErrorMessage(response.errorMessage)
                    }
                    is NetworkResponse.Success -> {
                        setLoading(false)
                        registrationScreenState.value = registrationScreenState.value.copy(isSignedIn = true)
                        onSuccess()
                    }
                    is NetworkResponse.Loading -> {
                        setLoading(true)
                    }
                }
            }
        }
    }
}