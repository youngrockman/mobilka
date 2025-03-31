package com.example.shoesapptest.screen.signin

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoesapptest.data.remote.network.NetworkResponse
import com.example.shoesapptest.data.remote.network.dto.AuthorizationRequest
import com.example.shoesapptest.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SignInViewModel(private val authUseCase: AuthUseCase) : ViewModel() {
    var signInState = mutableStateOf(SignInState())
        private set

    val emailHasError = derivedStateOf {
        if (signInState.value.email.isEmpty()) return@derivedStateOf false
        !android.util.Patterns.EMAIL_ADDRESS.matcher(signInState.value.email).matches()
    }

    fun setEmail(email: String) {
        signInState.value = signInState.value.copy(email = email)
    }

    fun setPassword(password: String) {
        signInState.value = signInState.value.copy(password = password)
    }

    fun setErrorMessage(message: String?) {
        signInState.value = signInState.value.copy(errorMessage = message)
    }

    private fun setLoading(isLoading: Boolean) {
        signInState.value = signInState.value.copy(isLoading = isLoading)
    }

    fun signIn(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val authorizationRequest = AuthorizationRequest(
                email = signInState.value.email,
                password = signInState.value.password
            )

            authUseCase.authorization(authorizationRequest).collect { response ->
                when (response) {
                    is NetworkResponse.Error -> {
                        setLoading(false)
                        setErrorMessage(response.errorMessage)
                    }
                    is NetworkResponse.Success -> {
                        setLoading(false)
                        signInState.value = signInState.value.copy(isSignIn = true)
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