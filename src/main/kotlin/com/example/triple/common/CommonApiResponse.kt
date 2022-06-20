package com.example.triple.common

import com.example.triple.dto.ErrorDto

data class CommonApiResponse(
    val code: String = "",
    val message: String = "",
    val data: Any,
) {
    companion object {
        fun success(data: Any): CommonApiResponse =
            CommonApiResponse(code = "Success", message = "성공했습니다.", data = data)

        fun error(errorDto: ErrorDto): CommonApiResponse =
            CommonApiResponse(code = errorDto.errorCode, message = errorDto.message, data = errorDto)
    }
}
