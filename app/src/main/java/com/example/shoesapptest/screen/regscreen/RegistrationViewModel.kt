package com.example.shoesapptest.screen.regscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoesapptest.data.remote.network.auth.RegistrationRequest
import com.example.shoesapptest.data.repository.AuthRepository
import kotlinx.coroutines.launch

class RegistrationViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    private var _registration = mutableStateOf(Registration())
    val registration = _registration

    val emailHasError get() = !android.util.Patterns.EMAIL_ADDRESS.matcher(_registration.value.email).matches()

    fun setEmail(email: String) {
        _registration.value = _registration.value.copy(email = email)
    }

    fun setPassword(password: String) {
        _registration.value = _registration.value.copy(password = password)
    }

    fun setUserName(name: String) {
        _registration.value = _registration.value.copy(name = name)
    }

    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val response = repository.registration(
                    RegistrationRequest(
                        userName = _registration.value.name,
                        email = _registration.value.email,
                        password = _registration.value.password
                    )
                )


                onSuccess()
            } catch (e: Exception) {
                _registration.value = _registration.value.copy(errorMessage = "Ошибка регистрации: ${e.message}")
            }
        }
    }
}