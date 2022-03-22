package com.hackaprende.dogedex.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.model.User
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    var user = mutableStateOf<User?>(null)
        private set

    var status = mutableStateOf<ApiResponseStatus<User>?>(null)
        private set

    private val authRepository = AuthRepository()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleResponseStatus(
                authRepository.login(
                    email,
                    password
                )
            )
        }
    }

    fun signUp(
        email: String, password: String,
        passwordConfirmation: String
    ) {
        viewModelScope.launch {
            status.value = ApiResponseStatus.Loading()
            handleResponseStatus(
                authRepository.signUp(
                    email,
                    password, passwordConfirmation
                )
            )
        }
    }

    private fun handleResponseStatus(apiResponseStatus: ApiResponseStatus<User>) {
        if (apiResponseStatus is ApiResponseStatus.Success) {
            user.value = apiResponseStatus.data!!
        }

        status.value = apiResponseStatus
    }

    fun resetApiResponseStatus() {
        status.value = null
    }
}