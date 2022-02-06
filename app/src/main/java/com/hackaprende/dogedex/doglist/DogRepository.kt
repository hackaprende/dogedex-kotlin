package com.hackaprende.dogedex.doglist

import com.hackaprende.dogedex.model.Dog
import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.api.DogsApi.retrofitService
import com.hackaprende.dogedex.api.dto.AddDogToUserDTO
import com.hackaprende.dogedex.api.dto.DogDTOMapper
import com.hackaprende.dogedex.api.makeNetworkCall
import java.lang.Exception

class DogRepository {

    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogListApiResponse = retrofitService.getAllDogs()
        val dogDTOList = dogListApiResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
    }

    suspend fun addDogToUser(dogId: Long): ApiResponseStatus<Any> = makeNetworkCall {
        val addDogToUserDTO = AddDogToUserDTO(dogId)
        val defaultResponse = retrofitService.addDogToUser(addDogToUserDTO)

        if (!defaultResponse.isSuccess) {
            throw Exception(defaultResponse.message)
        }
    }
}