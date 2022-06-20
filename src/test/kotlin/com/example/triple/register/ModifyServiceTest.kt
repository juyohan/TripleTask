package com.example.triple.register

import com.example.triple.dto.EventDto
import com.example.triple.model.Review
import com.example.triple.model.Reviewer
import com.example.triple.model.enumeration.ActionType
import com.example.triple.model.enumeration.ReviewActionType
import com.example.triple.repository.ReviewRepository
import com.example.triple.repository.ReviewerRepository
import com.example.triple.service.ReviewEventModifyService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import kotlin.test.assertEquals

@SpringBootTest
class ModifyServiceTest @Autowired constructor(
    val reviewRepository: ReviewRepository,
    val reviewerRepository: ReviewerRepository,
    val reviewEventModifyService: ReviewEventModifyService
) {

    @BeforeEach
    @Transactional
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
            contentLength = 3,
            photoCount = 0,
            reviewer = reviewer,
            isFirst = false,
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
    fun `content 를 비워두었을 때, 테스트`() {
        val eventDto = EventDto(
            type = ActionType.REVIEW,
            action = ReviewActionType.MOD,
            reviewId = "1",
            content = "",
            attachedPhotoIds = mutableListOf(),
            userId = "yo",
            placeId = "a"
        )

        val reviewerDto = reviewEventModifyService.modifyEvent(eventDto)
        assertEquals(reviewerDto.point, 2)
    }

    @Test
    fun `사진을 삭제했을 때, 테스트`() {
        val eventDto = EventDto(
            type = ActionType.REVIEW,
            action = ReviewActionType.MOD,
            reviewId = "2",
            content = "ㄴㅇㄹ",
            attachedPhotoIds = mutableListOf(),
            userId = "yo",
            placeId = "b"
        )

        val reviewerDto = reviewEventModifyService.modifyEvent(eventDto)
        assertEquals(reviewerDto.point, 2)
    }

    @Test
    fun `사진과 content 둘 다 삭제했을 때, 테스트`() {
        val eventDto = EventDto(
            type = ActionType.REVIEW,
            action = ReviewActionType.MOD,
            reviewId = "3",
            content = "",
            attachedPhotoIds = mutableListOf(),
            userId = "yo",
            placeId = "c"
        )

        val reviewerDto = reviewEventModifyService.modifyEvent(eventDto)
        assertEquals(reviewerDto.point, 1)
    }
}
