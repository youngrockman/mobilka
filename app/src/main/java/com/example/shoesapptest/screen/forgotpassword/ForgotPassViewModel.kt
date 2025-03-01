package com.example.shoesapptest.screen.forgotpassword

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ForgotPassViewModel: ViewModel(){
    var changePass = mutableStateOf(ChangePass())
        private set

    val emailHasError = derivedStateOf {
        if(changePass.value.email.isEmpty()) return@derivedStateOf false
        !android.util.Patterns.EMAIL_ADDRESS.matcher(changePass.value.email).matches()
    }

    fun setEmail(email: String){
        changePass.value = changePass.value.copy(email = email)
    }
}
