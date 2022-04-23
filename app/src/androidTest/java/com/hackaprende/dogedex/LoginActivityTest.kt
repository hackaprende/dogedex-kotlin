package com.hackaprende.dogedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import coil.annotation.ExperimentalCoilApi
import com.hackaprende.dogedex.core.api.ApiResponseStatus
import com.hackaprende.dogedex.camera.auth.AuthTasks
import com.hackaprende.dogedex.camera.auth.LoginActivity
import com.hackaprende.dogedex.camera.di.AuthTasksModule
import com.hackaprende.dogedex.core.model.User
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@UninstallModules(com.hackaprende.dogedex.camera.di.AuthTasksModule::class)
@HiltAndroidTest
class LoginActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<LoginActivity>()

    class FakeAuthRepository @Inject constructor(): AuthTasks {
        override suspend fun login(email: String, password: String): ApiResponseStatus<User> {
            return ApiResponseStatus.Success(
                User(1L, "hackaprende@gmail.com", "ubycasb67878asd")
            )
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

    @Test
    fun mainActivityOpensAfterUserLogin() {
        val context = composeTestRule.activity

        composeTestRule
            .onNodeWithText(context.getString(R.string.login))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithTag(useUnmergedTree = true, testTag = "email-field")
            .performTextInput("hackaprende@gmail.com")

        composeTestRule
            .onNodeWithTag(useUnmergedTree = true, testTag = "password-field")
            .performTextInput("test1234")

        composeTestRule
            .onNodeWithText(context.getString(R.string.login))
            .performClick()

        onView(withId(R.id.take_photo_fab)).check(matches(isDisplayed()))
    }
}