package com.hackaprende.dogedex

import androidx.camera.core.ImageProxy
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import coil.annotation.ExperimentalCoilApi
import com.hackaprende.dogedex.core.api.ApiResponseStatus
import com.hackaprende.dogedex.camera.di.ClassifierModule
import com.hackaprende.dogedex.camera.di.DogTasksModule
import com.hackaprende.dogedex.core.doglist.DogTasks
import com.hackaprende.dogedex.camera.machinelearning.ClassifierTasks
import com.hackaprende.dogedex.camera.machinelearning.DogRecognition
import com.hackaprende.dogedex.camera.main.MainActivity
import com.hackaprende.dogedex.core.model.Dog
import com.hackaprende.dogedex.core.testutils.EspressoIdlingResource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@UninstallModules(com.hackaprende.dogedex.camera.di.DogTasksModule::class, com.hackaprende.dogedex.camera.di.ClassifierModule::class)
@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @get:Rule(order = 2)
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    class FakeDogRepository @Inject constructor(): DogTasks {
        override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
            return ApiResponseStatus.Success(
                listOf(
                    Dog(
                        1, 1, "", "", "", "",
                        "", "", "", "", "",
                        inCollection = true
                    ),
                    Dog(
                        19, 23, "", "", "", "",
                        "", "", "", "", "",
                        inCollection = false
                    ),
                )
            )
        }

        override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
            TODO("Not yet implemented")
        }

        override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
            return ApiResponseStatus.Success(
                Dog(
                    89, 70, "Chow chow", "", "", "",
                    "", "", "", "", "",
                    inCollection = true
                )
            )
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class DogTasksTestModule {

        @Binds
        abstract fun bindDogTasks(
            fakeDogRepository: FakeDogRepository
        ): DogTasks
    }

    class FakeClassifierRepository @Inject constructor(): ClassifierTasks {
        override suspend fun recognizeImage(imageProxy: ImageProxy): DogRecognition {
            return DogRecognition("ajsncuinasdc", 100.0f)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    abstract class ClassifierTestModule {
        @Binds
        abstract fun bindClassifierTasks(
            fakeClassifierRepository: FakeClassifierRepository
        ): ClassifierTasks
    }

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun showAllFab() {
        onView(withId(R.id.take_photo_fab)).check(matches(isDisplayed()))
        onView(withId(R.id.dog_list_fab)).check(matches(isDisplayed()))
        onView(withId(R.id.settings_fab)).check(matches(isDisplayed()))
    }

    @Test
    fun dogListOpensWhenClickingButton() {
        onView(withId(R.id.dog_list_fab)).perform(click())

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val string = context.getString(R.string.my_dog_collection)
        composeTestRule.onNodeWithText(string).assertIsDisplayed()
    }

    @Test
    fun whenRecognizingDogDetailsScreenOpens() {
        onView(withId(R.id.take_photo_fab)).perform(click())
        composeTestRule.onNodeWithTag(testTag = "close-details-screen-fab").assertIsDisplayed()
    }
}