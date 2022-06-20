package com.example.triple.model.enumeration

import com.example.triple.dto.ReviewerDto

enum class OperatorType(
    var value: Int = 0
) {
    PLUS(1),
    MINUS(-1);

    companion object {
        fun operator(reviewerDto: ReviewerDto, type: OperatorType): OperatorType {
            reviewerDto.point += type.value
            return type
        }
    }
}
