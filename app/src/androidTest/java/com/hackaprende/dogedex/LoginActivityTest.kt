package com.hackaprende.dogedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import coil.annotation.ExperimentalCoilApi
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.auth.AuthRepository
import com.hackaprende.dogedex.auth.AuthTasks
import com.hackaprende.dogedex.auth.LoginActivity
import com.hackaprende.dogedex.di.AuthTasksModule
import com.hackaprende.dogedex.di.DogTasksModule
import com.hackaprende.dogedex.model.User
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Rule
import javax.inject.Inject

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@UninstallModules(AuthTasksModule::class)
@HiltAndroidTest
class LoginActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<LoginActivity>()

    class FakeAuthRepository @Inject constructor(): AuthTasks {
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

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class AuthTasksTestModule {

        @Binds
        abstract fun bindDogTasks(
            fakeAuthRepository: FakeAuthRepository
        ): AuthTasks
    }
}