package com.hackaprende.dogedex.api.dto

import com.hackaprende.dogedex.model.User

class UserDTOMapper {
    fun fromUserDTOToUserDomain(userDTO: UserDTO) =
        User(userDTO.id, userDTO.email, userDTO.authenticationToken)
}