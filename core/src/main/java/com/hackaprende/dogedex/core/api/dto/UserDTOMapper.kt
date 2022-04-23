package com.hackaprende.dogedex.core.api.dto

import com.hackaprende.dogedex.core.model.User

class UserDTOMapper {
    fun fromUserDTOToUserDomain(userDTO: UserDTO) =
        User(userDTO.id, userDTO.email, userDTO.authenticationToken)
}