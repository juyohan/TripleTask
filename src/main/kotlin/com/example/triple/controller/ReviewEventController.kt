package com.example.triple.controller

import com.example.triple.common.CommonApiResponse
import com.example.triple.common.CommonApiResponse.Companion.success
import com.example.triple.dto.EventDto
import com.example.triple.exception.BusinessException
import com.example.triple.exception.Exceptions
import com.example.triple.model.enumeration.ReviewActionType
import com.example.triple.service.ReviewEventDeleteService
import com.example.triple.service.ReviewEventModifyService
import com.example.triple.service.ReviewEventRegisterService
import com.example.triple.service.common.ReviewerCommonService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import org.springframework.http.HttpStatus.OK

@RestController
@RequestMapping("/events")
class ReviewEventController(
    val reviewEventRegisterService: ReviewEventRegisterService,
    val reviewEventDeleteService: ReviewEventDeleteService,
    val reviewEventModifyService: ReviewEventModifyService,
    val reviewerCommonService: ReviewerCommonService,
) {

    @PostMapping
    fun saveEvent(
        eventDto: EventDto
    ): ResponseEntity<CommonApiResponse> =
        ResponseEntity(
            success(
                when (eventDto.action) {
                    ReviewActionType.ADD -> reviewEventRegisterService.addReviewEvent(eventDto)
                    ReviewActionType.DELETE -> reviewEventDeleteService.deleteEvent(eventDto)
                    ReviewActionType.MOD -> reviewEventModifyService.modifyEvent(eventDto)
                    else -> throw BusinessException(Exceptions.NO_ACTION_TYPE)
                }
            ), OK
        )

    @GetMapping
    fun getReviewer(@RequestParam userId: String): ResponseEntity<CommonApiResponse>
    = ResponseEntity(success(reviewerCommonService.findOneReviewer(userId)), OK)
}
