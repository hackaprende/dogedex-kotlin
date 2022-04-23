package com.hackaprende.dogedex

import com.hackaprende.dogedex.core.api.ApiResponseStatus
import com.hackaprende.dogedex.core.api.ApiService
import com.hackaprende.dogedex.core.api.dto.AddDogToUserDTO
import com.hackaprende.dogedex.core.api.dto.DogDTO
import com.hackaprende.dogedex.core.api.dto.LoginDTO
import com.hackaprende.dogedex.core.api.dto.SignUpDTO
import com.hackaprende.dogedex.core.api.responses.*
import com.hackaprende.dogedex.core.doglist.DogRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
class DogRepositoryTest {

    @Test
    fun testGetDogCollectionSuccess(): Unit = runBlocking {
        class FakeApiService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(
                        dogs = listOf(
                            DogDTO(
                                1, 1, "Wartoortle", "", "", "",
                                "", "", "", "", "",
                            ),
                            DogDTO(
                                19, 2, "Charmeleon", "", "", "",
                                "", "", "", "", "",
                            ),
                        )
                    )
                )
            }

            override suspend fun signUp(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDTO: LoginDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(
                        dogs = listOf(
                            DogDTO(
                                19, 2, "Charmeleon", "", "", "",
                                "", "", "", "", "",
                            ),
                        )
                    )
                )
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }

        }

        val dogRepository = DogRepository(
            apiService = FakeApiService(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogCollection()
        assert(apiResponseStatus is ApiResponseStatus.Success)
        val dogCollection = (apiResponseStatus as ApiResponseStatus.Success).data
        assertEquals(2, dogCollection.size)
        assertEquals("Charmeleon", dogCollection[1].name)
        assertEquals("", dogCollection[0].name)
    }

    @Test
    fun testGetAllDogsError(): Unit = runBlocking {
        class FakeApiService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                throw UnknownHostException()
            }

            override suspend fun signUp(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDTO: LoginDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                return DogListApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogListResponse(
                        dogs = listOf(
                            DogDTO(
                                19, 2, "Charmeleon", "", "", "",
                                "", "", "", "", "",
                            ),
                        )
                    )
                )
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                TODO("Not yet implemented")
            }

        }

        val dogRepository = DogRepository(
            apiService = FakeApiService(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogCollection()
        assert(apiResponseStatus is ApiResponseStatus.Error)
        assertEquals(R.string.unknown_host_exception_error,
            (apiResponseStatus as ApiResponseStatus.Error).messageId)
    }

    @Test
    fun getDogByMlSuccess() = runBlocking {
        val resultDogId = 19L

        class FakeApiService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDTO: LoginDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                return DogApiResponse(
                    message = "",
                    isSuccess = true,
                    data = DogResponse(
                        dog = DogDTO(
                            resultDogId, 2, "Charmeleon", "", "", "",
                            "", "", "", "", "",
                        )
                    )
                )
            }
        }

        val dogRepository = DogRepository(
            apiService = FakeApiService(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogByMlId("asijbhdcuhab")
        assert(apiResponseStatus is ApiResponseStatus.Success)
        assertEquals(resultDogId, (apiResponseStatus as ApiResponseStatus.Success).data.id)
    }

    @Test
    fun getDogByMlError() = runBlocking {
        val resultDogId = 19L

        class FakeApiService : ApiService {
            override suspend fun getAllDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun signUp(signUpDTO: SignUpDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun login(loginDTO: LoginDTO): AuthApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun addDogToUser(addDogToUserDTO: AddDogToUserDTO): DefaultResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getUserDogs(): DogListApiResponse {
                TODO("Not yet implemented")
            }

            override suspend fun getDogByMlId(mlId: String): DogApiResponse {
                return DogApiResponse(
                    message = "error_getting_dog_by_ml_id",
                    isSuccess = false,
                    data = DogResponse(
                        dog = DogDTO(
                            resultDogId, 2, "Charmeleon", "", "", "",
                            "", "", "", "", "",
                        )
                    )
                )
            }
        }

        val dogRepository = DogRepository(
            apiService = FakeApiService(),
            dispatcher = TestCoroutineDispatcher()
        )

        val apiResponseStatus = dogRepository.getDogByMlId("asijbhdcuhab")
        assert(apiResponseStatus is ApiResponseStatus.Error)
        assertEquals(R.string.unknown_error, (apiResponseStatus as ApiResponseStatus.Error).messageId)
    }
}