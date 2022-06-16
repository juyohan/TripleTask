package com.example.triple.exception

import org.springframework.http.HttpStatus

enum class Exceptions(
    val status: HttpStatus,
    val code: String,
    val message: String,
) {
    NO_ACTION_TYPE(HttpStatus.NOT_FOUND, "ACTION_TYPE_ERROR_01", "정해지지 않은 동작 타입입니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_ERROR_01", "없는 사용자입니다."),

    REVIEW_EXIST(HttpStatus.CONFLICT, "REVIEW_ERROR_01", "이미 리뷰를 작성하셨습니다."),
}
