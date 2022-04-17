package com.hackaprende.dogedex.api

import com.hackaprende.dogedex.*
import com.hackaprende.dogedex.api.dto.AddDogToUserDTO
import com.hackaprende.dogedex.api.dto.LoginDTO
import com.hackaprende.dogedex.api.dto.SignUpDTO
import com.hackaprende.dogedex.api.responses.AuthApiResponse
import com.hackaprende.dogedex.api.responses.DefaultResponse
import com.hackaprende.dogedex.api.responses.DogApiResponse
import com.hackaprende.dogedex.api.responses.DogListApiResponse
import retrofit2.http.*

interface ApiService {
    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs(): DogListApiResponse

    @POST(SIGN_UP_URL)
    suspend fun signUp(@Body signUpDTO: SignUpDTO): AuthApiResponse

    @POST(SIGN_IN_URL)
    suspend fun login(@Body loginDTO: LoginDTO): AuthApiResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @POST(ADD_DOG_TO_USER_URL)
    suspend fun addDogToUser(@Body addDogToUserDTO: AddDogToUserDTO): DefaultResponse

    @Headers("${ApiServiceInterceptor.NEEDS_AUTH_HEADER_KEY}: true")
    @GET(GET_USER_DOGS_URL)
    suspend fun getUserDogs(): DogListApiResponse

    @GET(GET_DOG_BY_ML_ID)
    suspend fun getDogByMlId(@Query("ml_id") mlId: String): DogApiResponse
}
