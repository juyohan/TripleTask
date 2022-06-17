package com.example.triple.model.enumeration

import com.example.triple.model.Reviewer

enum class OperatorType(
    var value: Int = 0
) {
    PLUS(1),
    MINUS(-1);

    companion object {
        fun operator(reviewer: Reviewer, type: OperatorType): OperatorType {
            reviewer.points += type.value
            return type
        }
    }
}
