package com.hackaprende.dogedex

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.hackaprende.dogedex.core.api.ApiResponseStatus
import com.hackaprende.dogedex.camera.auth.AuthScreen
import com.hackaprende.dogedex.camera.auth.AuthTasks
import com.hackaprende.dogedex.camera.auth.AuthViewModel
import com.hackaprende.dogedex.core.model.User
import org.junit.Rule
import org.junit.Test

class AuthScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testTappingRegisterButtonOpenSignUpScreen() {
        class FakeAuthRepository: AuthTasks {
            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String
            ): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }

        }

        val viewModel = AuthViewModel(
            authRepository = FakeAuthRepository()
        )

        composeTestRule.setContent {
            AuthScreen(
                onUserLoggedIn = { },
                authViewModel = viewModel
            )
        }

        composeTestRule.onNodeWithTag(testTag = "login-button").assertIsDisplayed()
        composeTestRule.onNodeWithTag(testTag = "login-screen-register-button").performClick()
        composeTestRule.onNodeWithTag(testTag = "sign-up-button").assertIsDisplayed()
    }

    @Test
    fun testEmailErrorShowsIfTappingLoginButtonAndNotEmail() {
        class FakeAuthRepository: AuthTasks {
            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String
            ): ApiResponseStatus<User> {
                TODO("Not yet implemented")
            }

        }

        val viewModel = AuthViewModel(
            authRepository = FakeAuthRepository()
        )

        composeTestRule.setContent {
            AuthScreen(
                onUserLoggedIn = { },
                authViewModel = viewModel
            )
        }

        composeTestRule.onNodeWithTag(testTag = "login-button").performClick()
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "email-field-error").assertIsDisplayed()
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "email-field").performTextInput("hackaprende@gmail.com")
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "login-button").performClick()
        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "password-field-error").assertIsDisplayed()
    }
}