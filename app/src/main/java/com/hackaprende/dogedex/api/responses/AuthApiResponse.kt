package com.hackaprende.dogedex.api.responses

import com.squareup.moshi.Json

class AuthApiResponse(
    val message: String,
    @field:Json(name = "is_success") val isSuccess: Boolean,
    val data: UserResponse,
)
