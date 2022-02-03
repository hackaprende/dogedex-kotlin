package com.hackaprende.dogedex.auth

import com.hackaprende.dogedex.api.ApiResponseStatus
import com.hackaprende.dogedex.api.DogsApi
import com.hackaprende.dogedex.api.dto.LoginDTO
import com.hackaprende.dogedex.api.dto.SignUpDTO
import com.hackaprende.dogedex.api.dto.UserDTOMapper
import com.hackaprende.dogedex.api.makeNetworkCall
import com.hackaprende.dogedex.model.User

class AuthRepository {

    suspend fun login(email: String, password: String): ApiResponseStatus<User> = makeNetworkCall {
        val loginDTO = LoginDTO(email, password)
        val loginResponse = DogsApi.retrofitService.login(loginDTO)

        if (!loginResponse.isSuccess) {
            throw Exception(loginResponse.message)
        }

        val userDTO = loginResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOToUserDomain(userDTO)
    }

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