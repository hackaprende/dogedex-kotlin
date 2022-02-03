package com.hackaprende.dogedex.api

import com.hackaprende.dogedex.BASE_URL
import com.hackaprende.dogedex.Dog
import com.hackaprende.dogedex.GET_ALL_DOGS_URL
import com.hackaprende.dogedex.SIGN_UP_URL
import com.hackaprende.dogedex.api.dto.SignUpDTO
import com.hackaprende.dogedex.api.responses.DogListApiResponse
import com.hackaprende.dogedex.api.responses.SignUpApiResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface ApiService {
    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs(): DogListApiResponse

    @POST(SIGN_UP_URL)
    suspend fun signUp(@Body signUpDTO: SignUpDTO): SignUpApiResponse
}

object DogsApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}