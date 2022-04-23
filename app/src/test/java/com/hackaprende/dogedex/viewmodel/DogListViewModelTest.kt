package com.hackaprende.dogedex.viewmodel

import com.hackaprende.dogedex.core.api.ApiResponseStatus
import com.hackaprende.dogedex.core.doglist.DogListViewModel
import com.hackaprende.dogedex.core.doglist.DogTasks
import com.hackaprende.dogedex.core.model.Dog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*

class DogListViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var dogedexCoroutineRule = DogedexCoroutineRule()

    @Test
    fun downloadDogListStatusesCorrect() {
        class FakeDogRepository : DogTasks {
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Success(
                    listOf(
                        Dog(
                            1, 1, "", "", "", "",
                            "", "", "", "", "",
                            inCollection = false
                        ),
                        Dog(
                            19, 2, "", "", "", "",
                            "", "", "", "", "",
                            inCollection = false
                        ),
                    )
                )
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(Unit)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                return ApiResponseStatus.Success(Dog(
                    1, 1, "", "", "", "",
                    "", "", "", "", "",
                    inCollection = false
                ))
            }

        }

        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        assertEquals(2, viewModel.dogList.value.size)
        assertEquals(19, viewModel.dogList.value[1].id)
        assert(viewModel.status.value is ApiResponseStatus.Success)
    }

    @Test
    fun downloadDogListErrorStatusesCorrect() {
        class FakeDogRepository : DogTasks {
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Error(messageId = 12)
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(Unit)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                return ApiResponseStatus.Success(Dog(
                    1, 1, "", "", "", "",
                    "", "", "", "", "",
                    inCollection = false
                ))
            }

        }

        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        assertEquals(0, viewModel.dogList.value.size)
        assert(viewModel.status.value is ApiResponseStatus.Error)
    }

    @Test
    fun resetStatusCorrect() {
        class FakeDogRepository : DogTasks {
            override suspend fun getDogCollection(): ApiResponseStatus<List<Dog>> {
                return ApiResponseStatus.Error(messageId = 12)
            }

            override suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> {
                return ApiResponseStatus.Success(Unit)
            }

            override suspend fun getDogByMlId(mlDogId: String): ApiResponseStatus<Dog> {
                return ApiResponseStatus.Success(Dog(
                    1, 1, "", "", "", "",
                    "", "", "", "", "",
                    inCollection = false
                ))
            }

        }

        val viewModel = DogListViewModel(
            dogRepository = FakeDogRepository()
        )

        assert(viewModel.status.value is ApiResponseStatus.Error)

        viewModel.resetApiResponseStatus()

        assert(viewModel.status.value == null)
    }
}