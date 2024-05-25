package com.example.loginwithfirebase.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginwithfirebase.data.AuthRepository
import com.example.loginwithfirebase.util.Resource
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _loginState = Channel<LoginState>()
    val loginState = _loginState.receiveAsFlow()

    private val _googleState = mutableStateOf(LoginGoogleState())
    val googleState: State<LoginGoogleState> = _googleState

    fun loginUser(email: String, password: String, home: () -> Unit) {
        viewModelScope.launch {
            repository.loginUser(email = email, password = password).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _loginState.send(LoginState(isSuccess = "Login Success"))
                        home()
                    }

                    is Resource.Loading -> {
                        _loginState.send(LoginState(isLoading = true))
                    }

                    is Resource.Error -> {
                        _loginState.send(LoginState(isError = result.message))
                    }
                }
            }
        }
    }

    fun loginByGoogle(credential: AuthCredential, home: () -> Unit) {
        viewModelScope.launch {
            repository.loginByGoogle(credential = credential).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _googleState.value = LoginGoogleState(success = result.data)
                        home()
                    }

                    is Resource.Loading -> {
                        _googleState.value = LoginGoogleState(loading = true)
                    }

                    is Resource.Error -> {
                        _googleState.value = LoginGoogleState(error = result.message)
                    }
                }
            }
        }
    }
}