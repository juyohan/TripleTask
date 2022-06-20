package com.example.triple.dto

import com.example.triple.model.EventLog
import com.example.triple.model.enumeration.OperatorType
import com.example.triple.model.enumeration.PointType
import com.example.triple.model.enumeration.ReviewActionType

data class ReviewEventLogDto(
    val id: Long = 0L,
    var actionType: ReviewActionType = ReviewActionType.ADD,
    var pointType: PointType = PointType.CONTENT,
    var operatorType: OperatorType = OperatorType.MINUS,
    val reviewerId: Long = 0L,
) {

    fun toEntity(pointType: PointType): EventLog =
        EventLog(
            id, actionType, pointType, operatorType
        )

    fun toEntity(operatorType: OperatorType): EventLog =
        EventLog(
            id, actionType, pointType, operatorType
        )
}
