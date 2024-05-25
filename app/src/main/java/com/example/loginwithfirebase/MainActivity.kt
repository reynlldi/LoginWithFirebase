package com.example.loginwithfirebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.loginwithfirebase.navigation.NavigationApp
import com.example.loginwithfirebase.ui.theme.LoginWithFirebaseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginWithFirebaseTheme {
                NavigationApp()
            }
        }
    }
}