package com.example.triple.register

import com.example.triple.dto.EventDto
import com.example.triple.model.Review
import com.example.triple.model.Reviewer
import com.example.triple.model.enumeration.ActionType
import com.example.triple.model.enumeration.ReviewActionType
import com.example.triple.repository.ReviewRepository
import com.example.triple.repository.ReviewerRepository
import com.example.triple.service.ReviewEventRegisterService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
class RegisterServiceTest @Autowired constructor(
    val eventRegisterService: ReviewEventRegisterService,
    val reviewerRepository: ReviewerRepository,
    val reviewRepository: ReviewRepository,
) {

    @BeforeEach
    fun `데이터 주입`() {
        val reviewer = Reviewer(
            id = 1,
            userId = "yo",
        )
        reviewerRepository.save(reviewer)

        val review = Review(
            id = 1,
            reviewId = "1",
            placeId = "a",
            contentLength = 3,
            photoCount = 0,
            reviewer = reviewer,
            isFirst = false,
        )
        reviewRepository.save(review)
    }

    @Test
    fun `리뷰 이벤트 내용물만 작성하여 저장`() {
        val eventDto = EventDto(
            type = ActionType.REVIEW,
            action = ReviewActionType.ADD,
            reviewId = "3",
            content = "좋아요!",
            attachedPhotoIds = mutableListOf(),
            userId = "yo",
            placeId = "a"
        )

        val reviewerDto = eventRegisterService.addReviewEvent(eventDto)
        assertEquals(reviewerDto.point, 1)
    }

    @Test
    fun `리뷰 이벤트 사진만 추가하여 저장`() {
        val eventDto = EventDto(
            type = ActionType.REVIEW,
            action = ReviewActionType.ADD,
            reviewId = "3",
            content = "",
            attachedPhotoIds = mutableListOf("photo1", "photo2"),
            userId = "yo",
            placeId = "a"
        )

        val reviewerDto = eventRegisterService.addReviewEvent(eventDto)
        assertEquals(reviewerDto.point, 1)
    }

    @Test
    fun `리뷰 이벤트 사진과 내용물 추가하여 저장`() {
        val eventDto = EventDto(
            type = ActionType.REVIEW,
            action = ReviewActionType.ADD,
            reviewId = "3",
            content = "ㄴㅇㄹㅁㄴㅇㄹ",
            attachedPhotoIds = mutableListOf("photo1", "photo2"),
            userId = "yo",
            placeId = "a"
        )

        val reviewerDto = eventRegisterService.addReviewEvent(eventDto)
        assertEquals(reviewerDto.point, 2)
    }

    @Test
    fun `리뷰 이벤트 첫 리뷰 저장`() {
        val eventDto = EventDto(
            type = ActionType.REVIEW,
            action = ReviewActionType.ADD,
            reviewId = "3",
            content = "",
            attachedPhotoIds = mutableListOf(),
            userId = "yo",
            placeId = "b"
        )

        val reviewerDto = eventRegisterService.addReviewEvent(eventDto)
        assertEquals(reviewerDto.point, 1)
    }
}
