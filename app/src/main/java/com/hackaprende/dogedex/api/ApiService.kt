package com.hackaprende.dogedex.api

import com.hackaprende.dogedex.BASE_URL
import com.hackaprende.dogedex.Dog
import com.hackaprende.dogedex.GET_ALL_DOGS_URL
import com.hackaprende.dogedex.api.responses.DogListApiResponse
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

interface ApiService {
    @GET(GET_ALL_DOGS_URL)
    suspend fun getAllDogs(): DogListApiResponse
}

object DogsApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}