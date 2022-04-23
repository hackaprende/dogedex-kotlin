package com.hackaprende.dogedex.viewmodel

import com.hackaprende.dogedex.R
import com.hackaprende.dogedex.core.api.ApiResponseStatus
import com.hackaprende.dogedex.camera.auth.AuthTasks
import com.hackaprende.dogedex.camera.auth.AuthViewModel
import com.hackaprende.dogedex.core.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

class AuthViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var dogedexCoroutineRule = DogedexCoroutineRule()

    @Test
    fun testLoginValidationsCorrect() {
        class FakeAuthRepository : AuthTasks {
            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(
                    User(1, "hackaprende@gmail.com", "")
                )
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(
                    User(1, "", "")
                )
            }

        }

        val viewModel = AuthViewModel(
            authRepository = FakeAuthRepository()
        )

        viewModel.login("", "test1234")

        assertEquals(
            R.string.email_is_not_valid,
            viewModel.emailError.value)

        viewModel.login("hackaprende@gmail.com", "")

        assertEquals(
            R.string.password_must_not_be_empty,
            viewModel.passwordError.value)
    }

    @Test
    fun testLoginStatesCorrect() {
        val fakeUser = User(
            1, "hackaprende@gmail.com",
            ""
        )
        class FakeAuthRepository : AuthTasks {
            override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(fakeUser)
            }

            override suspend fun signUp(
                email: String,
                password: String,
                passwordConfirmation: String
            ): ApiResponseStatus<User> {
                return ApiResponseStatus.Success(
                    User(1, "", "")
                )
            }

        }

        val viewModel = AuthViewModel(
            authRepository = FakeAuthRepository()
        )

        viewModel.login("hackaprende@gmail.com", "test1234")

        assertEquals(fakeUser.email, viewModel.user.value?.email)
    }
}