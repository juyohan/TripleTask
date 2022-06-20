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

// 리뷰 이벤트 수정 서비스
/**
 * 1. 리뷰 내용이 0자로 바뀌었을 때,
 * 2. 리뷰의 사진 첨부가 1개 이상 -> 0개로 변경되었을 때,
 * 2-1. 리뷰의 사진 첨부가 0개 -> 1개 이상으로 변경되었을 때,
 */
@Service
class ReviewEventModifyService(
    val reviewEventLogCommonService: ReviewEventLogCommonService,
    val reviewCommonService: ReviewCommonService,
    val reviewerCommonService: ReviewerCommonService,
) {

    @Transactional
    fun modifyEvent(eventDto: EventDto) {
        val reviewerDto: ReviewerDto = reviewerCommonService.findOneReviewer(userId = eventDto.userId)
        val reviewDto: ReviewDto = reviewCommonService.findOneReview(reviewId = eventDto.reviewId)
        val reviewEventLogDto = ReviewEventLogDto(
            actionType = eventDto.action,
            reviewerId = reviewerDto.id,
        )

        if (reviewDto.contentLength != eventDto.content.length) {
            modifyReviewContent(
                reviewEventLogDto = reviewEventLogDto,
                contentLength = reviewDto.contentLength,
                modifyContentLength = eventDto.content.length
            )
            reviewDto.contentLength = eventDto.content.length
        }

        if (reviewDto.photoCount != eventDto.attachedPhotoIds.size) {
            modifyReviewPhotoCount(
                reviewEventLogDto = reviewEventLogDto,
                photoCount = reviewDto.photoCount,
                modifyPhotoCount = eventDto.attachedPhotoIds.size
            )
            reviewDto.photoCount = eventDto.attachedPhotoIds.size
        }

        reviewCommonService.updateOneReview(reviewDto)
    }

    fun modifyReviewPhotoCount(reviewEventLogDto: ReviewEventLogDto, photoCount: Int, modifyPhotoCount: Int) {
        reviewEventLogDto.pointType = PointType.PHOTO

        when {
            // 0개 -> 여러개로 변경
            photoCount == 0 && modifyPhotoCount > 0 ->
                reviewEventLogCommonService.addReviewEventLog(
                    reviewEventLogDto = reviewEventLogDto,
                    operatorType = OperatorType.PLUS
                )

            // 여러개 -> 0개로 변경
            photoCount > 0 && modifyPhotoCount == 0 ->
                reviewEventLogCommonService.addReviewEventLog(
                    reviewEventLogDto = reviewEventLogDto,
                    operatorType = OperatorType.MINUS
                )
        }
    }

    fun modifyReviewContent(
        reviewEventLogDto: ReviewEventLogDto,
        contentLength: Int,
        modifyContentLength: Int
    ) {
        reviewEventLogDto.pointType = PointType.CONTENT

        when {
            // 0 -> 1자 이상
            contentLength == 0 && modifyContentLength > 0
            -> reviewEventLogCommonService.addReviewEventLog(
                reviewEventLogDto = reviewEventLogDto,
                operatorType = OperatorType.PLUS
            )

            // 1자 이상 -> 0
            contentLength > 0 && modifyContentLength == 0
            -> reviewEventLogCommonService.addReviewEventLog(
                reviewEventLogDto = reviewEventLogDto,
                operatorType = OperatorType.MINUS
            )

        }
    }
}
