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
 * 1. 리뷰를 삭제하면 삭제한 로그 저장
 * 2. 삭제하면, 점수 회수 (글만 있는지, 첫번째 글인지, 사진 있는지 확인)
 * 2-1. 리뷰 내용의 길이를 확인하여 점수를 받았다면, 회수
 * 2-2. 사진을 추가함으로써 점수를 받았다면, 회수
 * 2-3. 첫번째 리뷰로 보너스 점수를 받았다면, 회수
 */
@Service
class ReviewEventDeleteService(
    val reviewEventLogCommonService: ReviewEventLogCommonService,
    val reviewCommonService: ReviewCommonService,
    val reviewerCommonService: ReviewerCommonService,
) {
    /**
     * 각 조건에 만족하면 점수를 회수하고 해당 리뷰를 삭제하는 메소드
     *
     * @param eventDto 삭제 요청의 데이터가 들은 DTO
     *
     * @return
     * Reviewer Entity 가 update 쿼리를 한 뒤, 해당 데이터를 다시 접근해서 가져와야하기 때문에 1차 캐시에 존재하고 있는
     * Entity 를 접근하기 위해 getReferenceReviewer() 을 통해 가져옴
     */
    @Transactional
    fun deleteEvent(eventDto: EventDto): ReviewerDto {
        val reviewerDto: ReviewerDto = reviewerCommonService.findOneReviewer(eventDto.userId)
        val reviewDto: ReviewDto = reviewCommonService.findOneReview(eventDto.reviewId)

        val reviewEventLogDto = ReviewEventLogDto(
            actionType = eventDto.action,
            operatorType = OperatorType.MINUS,
            reviewerId = reviewerDto.id
        )

        // 2-1번
        if (reviewDto.contentLength > 0)
            reviewEventLogCommonService.addReviewEventLog(
                reviewEventLogDto = reviewEventLogDto,
                pointType = PointType.CONTENT,
            )
        // 2-2번
        if (reviewDto.photoCount > 0)
            reviewEventLogCommonService.addReviewEventLog(
                reviewEventLogDto = reviewEventLogDto,
                pointType = PointType.PHOTO,
            )
        // 2-3번
        if (reviewDto.isFirst)
            reviewEventLogCommonService.addReviewEventLog(
                reviewEventLogDto = reviewEventLogDto,
                pointType = PointType.FIRST_PLACE,
            )

        reviewCommonService.deleteOneReview(reviewDto.id)

        return reviewerCommonService.getReferenceReviewer(reviewerDto.id).toDto()
    }
}
