package com.example.triple.controller

import com.example.triple.dto.EventDto
import com.example.triple.exception.BusinessException
import com.example.triple.exception.Exceptions
import com.example.triple.model.enumeration.ReviewActionType
import com.example.triple.service.ReviewEventDeleteService
import com.example.triple.service.ReviewEventModifyService
import com.example.triple.service.ReviewEventRegisterService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/events")
class ReviewEventController(
    val reviewEventRegisterService: ReviewEventRegisterService,
    val reviewEventDeleteService: ReviewEventDeleteService,
    val reviewEventModifyService: ReviewEventModifyService,
) {
    @PostMapping
    fun eventSave(
        eventDto: EventDto.Request.EventDetails
    ) {
        when(eventDto.action) {
            ReviewActionType.ADD -> reviewEventRegisterService.addReviewEvent(eventDto)
            ReviewActionType.DELETE -> reviewEventDeleteService.deleteEvent(eventDto)
            ReviewActionType.MOD -> reviewEventModifyService.modifyEvent(eventDto)
            else -> throw BusinessException(Exceptions.NO_ACTION_TYPE)
        }
    }

    @GetMapping
    fun eventList() {

    }
}
