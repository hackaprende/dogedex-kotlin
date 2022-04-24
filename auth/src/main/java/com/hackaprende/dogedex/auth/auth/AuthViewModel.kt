package com.hackaprende.dogedex.auth.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hackaprende.dogedex.auth.auth.AuthTasks
import com.hackaprende.dogedex.core.R
import com.hackaprende.dogedex.core.api.ApiResponseStatus
import com.hackaprende.dogedex.core.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthTasks
) : ViewModel() {

    var user = mutableStateOf<User?>(null)
        private set

    var emailError = mutableStateOf<Int?>(null)
        private set

    var passwordError = mutableStateOf<Int?>(null)
        private set

    var confirmPasswordError = mutableStateOf<Int?>(null)
        private set

    var status = mutableStateOf<ApiResponseStatus<User>?>(null)
        private set

    fun login(email: String, password: String) {
        when {
            email.isEmpty() -> emailError.value = R.string.email_is_not_valid
            password.isEmpty() -> passwordError.value = R.string.password_must_not_be_empty
            else -> {
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
        }
    }

    fun signUp(
        email: String, password: String,
        passwordConfirmation: String
    ) {
        when {
            email.isEmpty() -> emailError.value = R.string.email_is_not_valid
            password.isEmpty() -> passwordError.value = R.string.password_must_not_be_empty
            passwordConfirmation.isEmpty() -> confirmPasswordError.value =
                R.string.password_must_not_be_empty
            password != passwordConfirmation -> {
                passwordError.value = R.string.passwords_do_not_match
                confirmPasswordError.value = R.string.passwords_do_not_match
            }
            else -> {
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
        }
    }

    fun resetErrors() {
        emailError.value = null
        passwordError.value = null
        confirmPasswordError.value = null
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