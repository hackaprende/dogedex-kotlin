package com.hackaprende.dogedex.auth.auth

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hackaprende.dogedex.core.api.ApiResponseStatus
import com.hackaprende.dogedex.auth.auth.AuthNavDestinations.LoginScreenDestination
import com.hackaprende.dogedex.auth.auth.AuthNavDestinations.SignUpScreenDestination
import com.hackaprende.dogedex.core.composables.ErrorDialog
import com.hackaprende.dogedex.core.composables.LoadingWheel
import com.hackaprende.dogedex.core.model.User

@Composable
fun AuthScreen(
    onUserLoggedIn: (User) -> Unit,
    authViewModel: AuthViewModel = hiltViewModel(),
) {
    val user = authViewModel.user

    val userValue = user.value
    if (userValue != null) {
        onUserLoggedIn(userValue)
    }

    val navController = rememberNavController()
    val status = authViewModel.status.value

    AuthNavHost(
        navController = navController,
        onLoginButtonClick = { email, password -> authViewModel.login(email, password) },
        onSignUpButtonClick = { email, password, confirmPassword ->
            authViewModel.signUp(email, password, confirmPassword) },
        authViewModel = authViewModel,
    )

    if (status is ApiResponseStatus.Loading) {
        LoadingWheel()
    } else if (status is ApiResponseStatus.Error) {
        ErrorDialog(status.messageId) { authViewModel.resetApiResponseStatus() }
    }
}

@Composable
private fun AuthNavHost(
    navController: NavHostController,
    onLoginButtonClick: (String, String) -> Unit,
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    authViewModel: AuthViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = LoginScreenDestination
    ) {
        composable(route = LoginScreenDestination) {
            LoginScreen(
                onLoginButtonClick = onLoginButtonClick,
                onRegisterButtonClick = {
                    navController.navigate(route = SignUpScreenDestination)
                },
                authViewModel = authViewModel,
            )
        }

        composable(route = SignUpScreenDestination) {
            SignUpScreen(
                onSignUpButtonClick = onSignUpButtonClick,
                onNavigationIconClick = { navController.navigateUp() },
                authViewModel = authViewModel,
            )
        }
    }
}