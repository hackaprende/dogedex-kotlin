package com.hackaprende.dogedex.auth.auth

import com.hackaprende.dogedex.core.api.ApiResponseStatus
import com.hackaprende.dogedex.core.api.ApiService
import com.hackaprende.dogedex.core.api.dto.LoginDTO
import com.hackaprende.dogedex.core.api.dto.SignUpDTO
import com.hackaprende.dogedex.core.api.dto.UserDTOMapper
import com.hackaprende.dogedex.core.api.makeNetworkCall
import com.hackaprende.dogedex.core.model.User
import javax.inject.Inject

interface AuthTasks {
    suspend fun login(email: String, password: String): ApiResponseStatus<User>
    suspend fun signUp(
        email: String, password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User>
}

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
) : AuthTasks {

    override suspend fun login(email: String, password: String): ApiResponseStatus<User> = makeNetworkCall {
        val loginDTO = LoginDTO(email, password)
        val loginResponse = apiService.login(loginDTO)

        if (!loginResponse.isSuccess) {
            throw Exception(loginResponse.message)
        }

        val userDTO = loginResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOToUserDomain(userDTO)
    }

    override suspend fun signUp(
        email: String, password: String,
        passwordConfirmation: String
    ): ApiResponseStatus<User> = makeNetworkCall {
        val signUpDTO = SignUpDTO(email, password, passwordConfirmation)
        val signUpResponse = apiService.signUp(signUpDTO)

        if (!signUpResponse.isSuccess) {
            throw Exception(signUpResponse.message)
        }

        val userDTO = signUpResponse.data.user
        val userDTOMapper = UserDTOMapper()
        userDTOMapper.fromUserDTOToUserDomain(userDTO)
    }
}