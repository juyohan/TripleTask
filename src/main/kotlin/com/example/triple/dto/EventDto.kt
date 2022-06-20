package com.example.triple.dto

import com.example.triple.model.enumeration.ActionType
import com.example.triple.model.enumeration.ReviewActionType

data class EventDto(
    val type: ActionType = ActionType.REVIEW,
    val action: ReviewActionType = ReviewActionType.ADD,
    val reviewId: String = "",
    val content: String = "",
    val attachedPhotoIds: MutableList<String> = mutableListOf(),
    val userId: String = "",
    val placeId: String = "",
)
