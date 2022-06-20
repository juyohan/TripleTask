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
 * 1. 리뷰 내용이 1자 이상 -> 0자일 때, 점수 회수
 * 1-1 0자 -> 1자 이상일 때, 점수 추가
 * 2. 리뷰의 사진 첨부가 1개 이상 -> 0개일 때, 점수 회수
 * 2-1. 0개 -> 1개 이상일 때, 점수 추가
 */
@Service
class ReviewEventModifyService(
    val reviewEventLogCommonService: ReviewEventLogCommonService,
    val reviewCommonService: ReviewCommonService,
    val reviewerCommonService: ReviewerCommonService,
) {

    /**
     * 수정 요청이 들어왔을 때, 수행하는 메소드
     *
     * @param eventDto 요청온 API 의 데이터
     *
     * @return
     * Reviewer Entity 가 update 쿼리를 한 뒤, 해당 데이터를 다시 접근해서 가져와야하기 때문에 1차 캐시에 존재하고 있는
     * Entity 를 접근하기 위해 getReferenceReviewer() 을 통해 가져옴
     */
    @Transactional
    fun modifyEvent(eventDto: EventDto): ReviewerDto {
        val reviewerDto: ReviewerDto = reviewerCommonService.findOneReviewer(userId = eventDto.userId)
        val reviewDto: ReviewDto = reviewCommonService.findOneReview(reviewId = eventDto.reviewId)
        val reviewEventLogDto = ReviewEventLogDto(
            actionType = eventDto.action,
            reviewerId = reviewerDto.id,
        )

        // 1번 조건
        if (reviewDto.contentLength != eventDto.content.length) {
            modifyReviewContent(
                reviewEventLogDto = reviewEventLogDto,
                contentLength = reviewDto.contentLength,
                modifyContentLength = eventDto.content.length
            )
            reviewDto.contentLength = eventDto.content.length
        }

        // 2번 조건
        if (reviewDto.photoCount != eventDto.attachedPhotoIds.size) {
            modifyReviewPhotoCount(
                reviewEventLogDto = reviewEventLogDto,
                photoCount = reviewDto.photoCount,
                modifyPhotoCount = eventDto.attachedPhotoIds.size
            )
            reviewDto.photoCount = eventDto.attachedPhotoIds.size
        }

        // 변경된 리뷰 업데이트
        reviewCommonService.updateOneReview(reviewDto)

        return reviewerCommonService.getReferenceReviewer(reviewerDto.id).toDto()
    }

    /**
     * 사진의 개수가 변경되었을 때, 수행하는 메소드
     *
     * @param reviewEventLogDto 로그를 저장하기 위해 사용하는 DTO 객체
     * @param photoCount 현재 리뷰가 가지고 있는 사진의 개수
     * @param modifyPhotoCount 수정 요청이 들어온 API 데이터가 가지고 있는 사진의 개수
     */
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

    /**
     * 리뷰 내용의 길이가 변경되었을 때, 수행하는 메소드
     *
     * @param reviewEventLogDto 로그를 저장하기 위해 사용하는 DTO 객체
     * @param contentLength 현재 리뷰로 작성되어있는 내용의 길이
     * @param modifyContentLength 수정 요청이 들어온 API 데이터가 가지고 있는 리뷰 내용의 길이
     */
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
