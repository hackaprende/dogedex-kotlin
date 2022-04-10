package com.hackaprende.dogedex

import androidx.camera.core.ImageProxy
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import coil.annotation.ExperimentalCoilApi
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.di.ClassifierModule
import com.hackaprende.dogedex.di.DogTasksModule
import com.hackaprende.dogedex.doglist.DogRepository
import com.hackaprende.dogedex.doglist.DogTasks
import com.hackaprende.dogedex.machinelearning.ClassifierRepository
import com.hackaprende.dogedex.machinelearning.ClassifierTasks
import com.hackaprende.dogedex.machinelearning.DogRecognition
import com.hackaprende.dogedex.main.MainActivity
import com.hackaprende.dogedex.model.Dog
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
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@UninstallModules(DogTasksModule::class, ClassifierModule::class)
@HiltAndroidTest
class MainActivityTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
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
            TODO("Not yet implemented")
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
            TODO("Not yet implemented")
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

    @Test
    fun showAllFab() {
        onView(withId(R.id.take_photo_fab)).check(matches(isDisplayed()))
        onView(withId(R.id.dog_list_fab)).check(matches(isDisplayed()))
        onView(withId(R.id.settings_fab)).check(matches(isDisplayed()))
    }
}