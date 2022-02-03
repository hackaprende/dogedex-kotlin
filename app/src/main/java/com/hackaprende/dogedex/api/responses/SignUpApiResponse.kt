package com.hackaprende.dogedex.api.responses

import com.squareup.moshi.Json

class SignUpApiResponse(
    val message: String,
    @field:Json(name = "is_success") val isSuccess: Boolean,
    val data: UserResponse,
)
