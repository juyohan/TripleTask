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

// 리뷰 이벤트 삭제 서비스
/**
 * 1. 리뷰를 삭제하면 삭제한 로그 저장
 * 2. 삭제하면, 점수 회수 (글만 있는지, 첫번째 글인지, 사진 있는지 확인)
 * 2-1. 1번째 리뷰일 때, Log 를 뒤져서 가져오는게 나은지, 아니면 Column 을 생성해서 찾는게 나은지.
 * 3. 삭제할 때, 작성이 되었다면, 추가 점수 없음. -> 카운트를 해줘서 갯수가 줄어들었는지, 안줄어들었는지 확인해서 판단
 */
@Service
class ReviewEventDeleteService(
    val reviewEventLogCommonService: ReviewEventLogCommonService,
    val reviewCommonService: ReviewCommonService,
    val reviewerCommonService: ReviewerCommonService,
) {
    fun deleteEvent(eventDto: EventDto) {
        val reviewerDto: ReviewerDto = reviewerCommonService.findOneReviewer(eventDto.userId)
        val reviewDto: ReviewDto = reviewCommonService.findOneReview(eventDto.reviewId)

        val reviewEventLogDto = ReviewEventLogDto(
            actionType = eventDto.action,
            operatorType = OperatorType.MINUS,
            reviewerId = reviewerDto.id
        )

        if (reviewDto.contentLength > 0)
            reviewEventLogCommonService.addReviewEventLog(
                reviewEventLogDto = reviewEventLogDto,
                pointType = PointType.CONTENT,
            )

        if (reviewDto.photoCount > 0)
            reviewEventLogCommonService.addReviewEventLog(
                reviewEventLogDto = reviewEventLogDto,
                pointType = PointType.PHOTO,
            )

        if (reviewDto.isFirst)
            reviewEventLogCommonService.addReviewEventLog(
                reviewEventLogDto = reviewEventLogDto,
                pointType = PointType.FIRST_PLACE,
            )

        reviewCommonService.deleteOneReview(reviewDto.reviewId)
    }


}
