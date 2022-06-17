package com.example.triple.dto

import com.example.triple.model.enumeration.ActionType
import com.example.triple.model.enumeration.ReviewActionType
import com.example.triple.model.enumeration.PointType

class EventDto {
    class Request {
        data class EventDetails(
            val type: ActionType = ActionType.REVIEW,
            val action: ReviewActionType = ReviewActionType.ADD,
            val reviewId: String = "",
            val content: String = "",
            val attachedPhotoIds: List<String> = listOf(),
            val userId: String = "",
            val placeId: String = "",
        ) {

        }
    }
}
