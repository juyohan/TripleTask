package com.example.triple.register

import com.example.triple.dto.EventDto
import com.example.triple.model.Review
import com.example.triple.model.Reviewer
import com.example.triple.model.enumeration.ActionType
import com.example.triple.model.enumeration.ReviewActionType
import com.example.triple.repository.ReviewRepository
import com.example.triple.repository.ReviewerRepository
import com.example.triple.service.ReviewEventDeleteService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
class DeleteServiceTest @Autowired constructor(
    val reviewEventDeleteService: ReviewEventDeleteService,
    val reviewRepository: ReviewRepository,
    val reviewerRepository: ReviewerRepository,
) {
    @BeforeEach
    fun `초기 데이터 저장`() {
        val reviewer = Reviewer(
            id = 1,
            userId = "yo",
            point = 3,
        )
        reviewerRepository.save(reviewer)

        val reviewList = mutableListOf<Review>()
        val review = Review(
            id = 1,
            reviewId = "1",
            placeId = "a",
            contentLength = 2,
            photoCount = 3,
            reviewer = reviewer,
            isFirst = true,
        )
        val review2 = Review(
            id = 2,
            reviewId = "2",
            placeId = "b",
            contentLength = 3,
            photoCount = 2,
            reviewer = reviewer,
            isFirst = false,
        )
        reviewList.add(review)
        reviewList.add(review2)
        val review3 = Review(
            id = 3,
            reviewId = "3",
            placeId = "c",
            contentLength = 3,
            photoCount = 2,
            reviewer = reviewer,
            isFirst = false,
        )
        reviewList.add(review3)
        reviewRepository.saveAll(reviewList)
    }

    @Test
    fun `첫 리뷰를 삭제`() {
        val eventDto = EventDto(
            type = ActionType.REVIEW,
            action = ReviewActionType.DELETE,
            reviewId = "1",
            content = "",
            attachedPhotoIds = mutableListOf(),
            userId = "yo",
            placeId = "a"
        )

        val reviewerDto = reviewEventDeleteService.deleteEvent(eventDto)
        assertEquals(reviewerDto.point, 2)
    }

    @Test
    fun `첫 리뷰에 내용이 담겨있는 리뷰를 삭제`() {
        val eventDto = EventDto(
            type = ActionType.REVIEW,
            action = ReviewActionType.DELETE,
            reviewId = "1",
            content = "",
            attachedPhotoIds = mutableListOf(),
            userId = "yo",
            placeId = "a"
        )

        val reviewerDto = reviewEventDeleteService.deleteEvent(eventDto)
        assertEquals(reviewerDto.point, 1)
    }

    @Test
    fun `첫 리뷰에 내용과 사진이 담겨있는 리뷰를 삭제`() {
        val eventDto = EventDto(
            type = ActionType.REVIEW,
            action = ReviewActionType.DELETE,
            reviewId = "1",
            content = "",
            attachedPhotoIds = mutableListOf(),
            userId = "yo",
            placeId = "a"
        )

        val reviewerDto = reviewEventDeleteService.deleteEvent(eventDto)
        assertEquals(reviewerDto.point, 0)
    }

    @Test
    fun `내용과 사진이 담겨있는 리뷰를 삭제`() {
        val eventDto = EventDto(
            type = ActionType.REVIEW,
            action = ReviewActionType.DELETE,
            reviewId = "2",
            content = "",
            attachedPhotoIds = mutableListOf(),
            userId = "yo",
            placeId = "b"
        )

        val reviewerDto = reviewEventDeleteService.deleteEvent(eventDto)
        assertEquals(reviewerDto.point, 1)
    }
}
