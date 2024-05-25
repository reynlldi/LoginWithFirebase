package com.example.loginwithfirebase.presentation.login

import com.google.firebase.auth.AuthResult

data class LoginGoogleState(
    val success: AuthResult? = null,
    val loading: Boolean = false,
    val error: String? = ""
)