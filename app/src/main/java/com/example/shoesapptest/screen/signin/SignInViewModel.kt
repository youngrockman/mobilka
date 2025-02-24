package com.example.shoesapptest.screen.signin

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SignInViewMode: ViewModel(){
    var signInState = mutableStateOf(SignInState())
            private set

    val emailHasError = derivedStateOf {
        if(signInState.value.email.isEmpty()) return@derivedStateOf false
        !android.util.Patterns.EMAIL_ADDRESS.matcher(signInState.value.email).matches()
    }

    fun setEmail(email: String){
        signInState.value = signInState.value.copy(email = email)
    }


    fun setPassword(password: String){
        signInState.value = signInState.value.copy(password = password)
    }



}