package com.example.triple


import com.example.triple.dto.EventDto
import com.example.triple.model.enumeration.ActionType
import com.example.triple.model.enumeration.ReviewActionType
import com.example.triple.repository.ReviewEventLogRepository
import com.example.triple.repository.ReviewerRepository
import com.example.triple.service.ReviewEventRegisterService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TripleApplicationTests(
    @Autowired
    val reviewEventRegisterService: ReviewEventRegisterService,
    @Autowired
    val reviewEventLogRepository: ReviewEventLogRepository,
    @Autowired
    val reviewerRepository: ReviewerRepository,
) {

    @BeforeEach
    fun `초기 데이터`() {
        val eventDto = EventDto(
            type = ActionType.REVIEW,
            action = ReviewActionType.ADD,
            reviewId = "240a0658-dc5f-4878-9381-ebb7b2667772",
            content = "좋아요!",
            attachedPhotoIds = mutableListOf("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"),
            userId = "3ede0ef2-92b7-4817-a5f3-0c575361f745",
            placeId = "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
        )
        val eventDto1 = EventDto(
            type = ActionType.REVIEW,
            action = ReviewActionType.ADD,
            reviewId = "1",
            content = "좋아요!",
            attachedPhotoIds = mutableListOf("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"),
            userId = "1",
            placeId = "1"
        )
        val eventDto2 = EventDto(
            type = ActionType.REVIEW,
            action = ReviewActionType.ADD,
            reviewId = "2",
            content = "좋아요!",
            attachedPhotoIds = mutableListOf("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"),
            userId = "2",
            placeId = "1"
        )
        val eventDto3 = EventDto(
            type = ActionType.REVIEW,
            action = ReviewActionType.ADD,
            reviewId = "1",
            content = "좋아요!",
            attachedPhotoIds = mutableListOf(),
            userId = "1",
            placeId = "2"
        )
//        reviewEventRepository.save()
    }

    @Test
    fun `리뷰를 작성 했는지, 안했는지 판단하는 테스트`() {
    }

    @Test
    fun `리뷰가 제대로 저장이 되는지 확인하는 테스트`() {
        val eventDto = EventDto(
            type = ActionType.REVIEW,
            action = ReviewActionType.ADD,
            reviewId = "240a0658-dc5f-4878-9381-ebb7b2667772",
            content = "좋아요!",
            attachedPhotoIds = mutableListOf(),
            userId = "3ede0ef2-92b7-4817-a5f3-0c575361f745",
            placeId = "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
        )

//        reviewEventRegisterService.addPhotoAndReview(
//            photos = eventDto.attachedPhotoIds,
//            reviewId = eventDto.reviewId,
//            userId = eventDto.userId,
//            content = eventDto.content,
//            placeId = eventDto.placeId
//        )


//        reviewEventLogRepository.findAll().stream().forEach { println(it) }
    }

}
