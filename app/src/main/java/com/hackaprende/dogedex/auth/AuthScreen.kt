package com.hackaprende.dogedex.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.auth.AuthNavDestinations.LoginScreenDestination
import com.hackaprende.dogedex.auth.AuthNavDestinations.SignUpScreenDestination
import com.hackaprende.dogedex.composables.ErrorDialog
import com.hackaprende.dogedex.composables.LoadingWheel
import com.hackaprende.dogedex.model.User

@Composable
fun AuthScreen(
    status: ApiResponseStatus<User>?,
    onLoginButtonClick: (String, String) -> Unit,
    onSignUpButtonClick: (email: String, password: String, passwordConfirmation: String) -> Unit,
    onErrorDialogDismiss: () -> Unit,
    authViewModel: AuthViewModel,
) {
    val navController = rememberNavController()

    AuthNavHost(
        navController = navController,
        onLoginButtonClick = onLoginButtonClick,
        onSignUpButtonClick = onSignUpButtonClick,
        authViewModel = authViewModel,
    )

    if (status is ApiResponseStatus.Loading) {
        LoadingWheel()
    } else if (status is ApiResponseStatus.Error) {
        ErrorDialog(status.messageId, onErrorDialogDismiss)
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