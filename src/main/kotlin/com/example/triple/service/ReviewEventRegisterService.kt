package com.example.triple.service

import com.example.triple.dto.EventDto
import com.example.triple.dto.ReviewDto
import com.example.triple.dto.ReviewEventLogDto
import com.example.triple.dto.ReviewerDto
import com.example.triple.model.enumeration.OperatorType
import com.example.triple.model.enumeration.PointType
import com.example.triple.service.common.ReviewCommonService
import com.example.triple.service.common.ReviewEventLogCommonService
import com.example.triple.service.common.ReviewerCommonService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * 1. 리뷰 내용이 0자 이상일 때
 * 2. 리뷰 첨부 사진이 1개 이상일 때
 * 3. 장소에 첫 리뷰를 작성했을 때
 */
@Service
class ReviewEventRegisterService(
    val reviewEventLogCommonService: ReviewEventLogCommonService,
    val reviewerCommonService: ReviewerCommonService,
    val reviewCommonService: ReviewCommonService,
) {
    /**
     * 이벤트 리뷰와 조건에 만족할 때마다 로그 DB에 저장하고 점수를 반영
     *
     * 조건 - 1. 리뷰 댓글의 내용이 1자 이상의 텍스트 작성일 때
     *       2. 리뷰에 사진을 1장 이상 첨부했을 때
     *       3. 해당 장소에 처음으로 리뷰를 작성했을 때
     *
     * 각 조건을 만족했을 때, 1점씩 부여하여 마지막에 점수를 반영시킴
     *
     * @param eventDto 요청받은 정보의 Dto
     */
    @Transactional
    fun addReviewEvent(eventDto: EventDto) {
        val reviewerDto: ReviewerDto = reviewerCommonService.findOrCreateReviewer(eventDto.userId)
        val reviewDto: ReviewDto = ReviewDto().createDto(eventDto, reviewerDto.id)
        val reviewEventLogDto = ReviewEventLogDto(
            actionType = eventDto.action,
            operatorType = OperatorType.PLUS,
            reviewerId = reviewerDto.id
        )

        // 1번 조건
        if (eventDto.content.isNotEmpty())
            reviewEventLogCommonService.addReviewEventLog(
                reviewEventLogDto = reviewEventLogDto,
                pointType = PointType.CONTENT
            )

        // 2번 조건
        if (eventDto.attachedPhotoIds.isNotEmpty())
            reviewEventLogCommonService.addReviewEventLog(
                reviewEventLogDto = reviewEventLogDto,
                pointType = PointType.PHOTO
            )

        // 3번 조건
        if (reviewCommonService.countReviewByPlaceId(eventDto.placeId) == 0) {
            reviewEventLogCommonService.addReviewEventLog(
                reviewEventLogDto = reviewEventLogDto,
                pointType = PointType.FIRST_PLACE
            )
            reviewDto.isFirst = true
        }

        // 리뷰 저장
        reviewCommonService.addReviewDto(reviewDto)
    }
}
