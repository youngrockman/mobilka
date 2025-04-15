package com.example.shoesapptest

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shoesapptest.domain.usecase.validator.EmailValidator
import com.example.shoesapptest.domain.usecase.validator.PasswordValidator

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameter
import org.junit.runners.Parameterized.Parameters

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(Parameterized::class)
class EmailValidationTest(private val email: String, private val expectedResult: Boolean) {

    @Test
    fun testEmailValidation() {
        val result = EmailValidator().validate(email)
        assertEquals(expectedResult, result)
    }

    companion object {
        @JvmStatic
        @Parameters
        fun getData() = listOf(

            arrayOf("test@mail.ru", true),
            arrayOf("user123@domain.ab", true),
            arrayOf("a@b.cd", true),


            arrayOf("Test@mail.ru", false),
            arrayOf("test@Mail.ru", false),
            arrayOf("test@mail.russia", false),
            arrayOf("test@mail.r", false),
            arrayOf("test@mail.1", false),
            arrayOf("test@.ru", false),
            arrayOf("@mail.ru", false),
            arrayOf("testmail.ru", false),
            arrayOf("test@mail..ru", false),
            arrayOf("test@mail.ru ", false),
            arrayOf(" test@mail.ru", false),
            arrayOf("", false)
        )
    }
}


@RunWith(Parameterized::class)
class PasswordValidationTest(private val password: String, private val expectedResult: Boolean) {

    @Test
    fun testPasswordValidation() {
        val result = PasswordValidator().validate(password)
        assertEquals(expectedResult, result)
    }

    companion object {
        @JvmStatic
        @Parameters
        fun getData() = listOf(

            arrayOf("ValidPass1!", true),
            arrayOf("NewPass2@", true),



            arrayOf("short", false),
            arrayOf("noDigits!", false),
            arrayOf("alllower1!", false),
            arrayOf("", false)
        )
    }
}