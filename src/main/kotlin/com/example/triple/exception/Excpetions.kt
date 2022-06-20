// ktlint-disable filename
package com.example.triple.exception

import org.springframework.http.HttpStatus

enum class Exceptions(
    val status: HttpStatus,
    val code: String,
    val message: String,
) {
    NO_ACTION_TYPE(HttpStatus.NOT_FOUND, "ACTION_TYPE_ERROR_01", "정해지지 않은 동작 타입입니다."),

    REVIEWER_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEWER_ERROR_01", "없는 작성자입니다."),

    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "REVIEW_ERROR_01", "존재하지 않는 리뷰입니다."),
}
