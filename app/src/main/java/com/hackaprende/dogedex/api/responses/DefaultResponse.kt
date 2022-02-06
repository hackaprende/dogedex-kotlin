package com.hackaprende.dogedex.api.responses

import com.squareup.moshi.Json

class DefaultResponse(
    val message: String,
    @field:Json(name = "is_success") val isSuccess: Boolean,
)