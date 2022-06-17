package com.example.triple.register

import com.example.triple.dto.EventDto
import com.example.triple.model.enumeration.ActionType
import com.example.triple.model.enumeration.ReviewActionType
import com.example.triple.repository.ReviewEventLogRepository
import com.example.triple.service.ReviewEventRegisterService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class RegisterServiceTest(
    @Autowired
    val eventRegisterService: ReviewEventRegisterService,
    @Autowired
    val reviewEventLogRepository: ReviewEventLogRepository,
) {

    @Test
    fun `리뷰 이벤트 저장`() {
        val eventDto = EventDto.Request.EventDetails(
            type = ActionType.REVIEW,
            action = ReviewActionType.ADD,
            reviewId = "240a0658-dc5f-4878-9381-ebb7b2667772",
            content = "좋아요!",
            attachedPhotoIds = listOf(),
            userId = "3ede0ef2-92b7-4817-a5f3-0c575361f745",
            placeId = "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
        )

        eventRegisterService.addReviewEvent(eventDto)

        reviewEventLogRepository.findAll().map { println(it) }
    }
}
