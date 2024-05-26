package com.example.loginwithfirebase.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginwithfirebase.data.AuthRepository
import com.example.loginwithfirebase.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    private val _registerState = Channel<RegisterState>()
    val registerState = _registerState.receiveAsFlow()

    fun registerUser(email: String, password: String, home: () -> Unit) {
        viewModelScope.launch {
            repository.registerUser(email = email, password = password).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _registerState.send(RegisterState(isSuccess = "Register Success"))
                        home()
                    }

                    is Resource.Loading -> {
                        _registerState.send(RegisterState(isLoading = true))
                    }

                    is Resource.Error -> {
                        _registerState.send(RegisterState(isError = result.message))
                    }
                }
            }
        }
    }
}