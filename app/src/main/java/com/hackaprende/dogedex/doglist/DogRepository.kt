package com.hackaprende.dogedex.doglist

import com.hackaprende.dogedex.Dog
import com.hackaprende.dogedex.api.DogsApi.retrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository {

    suspend fun downloadDogs(): List<Dog> {
        return withContext(Dispatchers.IO) {
            val dogListApiResponse = retrofitService.getAllDogs()
            dogListApiResponse.data.dogs
        }
    }
}