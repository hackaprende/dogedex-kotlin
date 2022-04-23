package com.hackaprende.dogedex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import coil.annotation.ExperimentalCoilApi
import com.hackaprende.dogedex.core.api.ApiResponseStatus
import com.hackaprende.dogedex.core.doglist.DogListScreen
import com.hackaprende.dogedex.core.doglist.DogListViewModel
import com.hackaprende.dogedex.core.doglist.DogTasks
import com.hackaprende.dogedex.core.model.Dog
import org.junit.Rule
import org.junit.Test

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
class DogListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun progressBarShowsWhenLoadingState() {
        class FakeDogRepository : DogTasks {
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Loading()
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                TODO("Not yet implemented")
            }
        }

        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        composeTestRule.setContent {
            DogListScreen(
                onNavigationIconClick = { },
                onDogClicked = { },
                viewModel = viewModel,
            )
        }

        composeTestRule.onNodeWithTag(testTag = "loading-wheel").assertIsDisplayed()
    }

    @Test
    fun errorDialogShowsIfErrorGettingDogs() {
        class FakeDogRepository : DogTasks {
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Error(messageId = R.string.there_was_an_error)
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                TODO("Not yet implemented")
            }
        }

        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        composeTestRule.setContent {
            DogListScreen(
                onNavigationIconClick = { },
                onDogClicked = { },
                viewModel = viewModel,
            )
        }

        composeTestRule.onNodeWithTag(testTag = "error-dialog").assertIsDisplayed()
    }

    @Test
    fun dogListShowsIfSuccessGettingDogs() {
        val dog1Name = "Chihuahua"
        val dog2Name = "Guillermo"
        class FakeDogRepository : DogTasks {
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Success(
                    listOf(
                        Dog(
                            1, 1, dog1Name, "", "", "",
                            "", "", "", "", "",
                            inCollection = true
                        ),
                        Dog(
                            19, 23, dog2Name, "", "", "",
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

        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        composeTestRule.setContent {
            DogListScreen(
                onNavigationIconClick = { },
                onDogClicked = { },
                viewModel = viewModel,
            )
        }

        composeTestRule.onNodeWithTag(useUnmergedTree = true, testTag = "dog-${dog1Name}").assertIsDisplayed()
        composeTestRule.onNodeWithText("23").assertIsDisplayed()
    }
}