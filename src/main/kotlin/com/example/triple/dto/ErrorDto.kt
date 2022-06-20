package com.example.triple.dto

import com.example.triple.exception.Exceptions
import javax.servlet.http.HttpServletRequest

data class ErrorDto(
    val httpStatus: String = "",
    val httpMethod: String = "",
    val errorCode: String = "",
    val message: String = "",
    val path: String = "",
) {
    fun toDto(
        request: HttpServletRequest,
        exceptions: Exceptions,
    ): ErrorDto =
        ErrorDto(
            httpStatus = exceptions.status.toString(),
            message = exceptions.message,
            errorCode = exceptions.code,
            httpMethod = request.method,
            path = request.requestURI
        )
}
