package com.example.shoesapptest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.shoesapp.ui.theme.MatuleTheme
import com.example.shoesapptest.screen.regscreen.RegisterAccountScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MatuleTheme {
                RegisterAccountScreen()
            }
        }
    }
}