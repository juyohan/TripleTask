package com.example.triple.dto

import com.example.triple.model.Reviewer

data class ReviewerDto(
    val id: Long = 0L,
    val userId: String = "",
    var point: Int = 0,
) {
    fun toEntity(): Reviewer =
        Reviewer(
            id = id,
            userId = userId,
            point = point,
        )
}
