package com.example.triple.exception

import com.example.triple.common.CommonApiResponse
import com.example.triple.dto.ErrorDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class GlobalExceptionAdvice : ResponseEntityExceptionHandler() {
    @ExceptionHandler(BusinessException::class)
    fun businessExceptionHandler(
        exceptions: Exceptions,
        request: HttpServletRequest
    ): ResponseEntity<CommonApiResponse> =
        ResponseEntity(
            CommonApiResponse.error(
                errorDto = ErrorDto().toDto(
                    request = request,
                    exceptions = exceptions,
                )
            ), HttpStatus.OK
        )
}
