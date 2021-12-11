package com.hackaprende.dogedex.api

import com.hackaprende.dogedex.Dog

sealed class ApiResponseStatus() {
    class Success(val dogList: List<Dog>): ApiResponseStatus()
    class Loading(): ApiResponseStatus()
    class Error(val messageId: Int): ApiResponseStatus()
}