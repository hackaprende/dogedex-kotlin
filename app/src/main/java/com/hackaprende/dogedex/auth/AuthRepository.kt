package com.hackaprende.dogedex.auth

import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.api.DogsApi
import com.hackaprende.dogedex.api.dto.SignUpDTO
import com.hackaprende.dogedex.api.dto.UserDTOMapper
import com.hackaprende.dogedex.api.makeNetworkCall
import com.hackaprende.dogedex.model.User

class AuthRepository {

    suspend fun signUp(email: String, password: String,
                       passwordConfirmation: String): ApiResponseStatus<User> = makeNetworkCall {
        val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
        val signUpResponse = DogsApi.retrofitService.signUp(signUpDTO)

        if (!signUpResponse.isSuccess) {
            throw Exception(signUpResponse.message)
        }

        val userDTO = signUpResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOToUserDomain(userDTO)
    }
}