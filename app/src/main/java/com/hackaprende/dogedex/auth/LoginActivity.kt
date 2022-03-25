package com.hackaprende.dogedex.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import coil.annotation.ExperimentalCoilApi
import com.hackaprende.dogedex.dogdetail.ui.theme.DogedexTheme
import com.hackaprende.dogedex.main.MainActivity
import com.hackaprende.dogedex.model.User

@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
class LoginActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val user = viewModel.user

            val userValue = user.value
            if (userValue != null) {
                User.setLoggedInUser(this, userValue)
                startMainActivity()
            }

            val status = viewModel.status

            DogedexTheme {
                AuthScreen(
                    status = status.value,
                    onLoginButtonClick = { email, password -> viewModel.login(email, password) },
                    onSignUpButtonClick = { email, password, confirmPassword ->
                        viewModel.signUp(email, password, confirmPassword)
                    },
                    onErrorDialogDismiss = ::resetApiResponseStatus,
                    authViewModel = viewModel,
                )
            }
        }
    }

    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}