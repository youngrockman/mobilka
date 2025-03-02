package com.example.shoesapptest.screen.regscreen

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegistrationViewModel: ViewModel(){
    var registration = mutableStateOf(Registration())
    private set

    val emailHasError = derivedStateOf {
        if(registration.value.email.isEmpty()) return@derivedStateOf false
        !android.util.Patterns.EMAIL_ADDRESS.matcher(registration.value.email).matches()
    }

    fun setEmail(email: String){
        registration.value = registration.value.copy(email = email)
    }


    fun setPassword(password: String){
        registration.value = registration.value.copy(password = password)
    }

    fun setUserName(name: String){
        registration.value = registration.value.copy(name = name)
    }



}