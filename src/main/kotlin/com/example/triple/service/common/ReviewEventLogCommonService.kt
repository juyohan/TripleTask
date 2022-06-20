package com.example.triple.service.common

import com.example.triple.dto.ReviewEventLogDto
import com.example.triple.model.enumeration.OperatorType
import com.example.triple.model.enumeration.PointType
import com.example.triple.repository.ReviewEventLogRepository
import org.springframework.stereotype.Service

@Service
class ReviewEventLogCommonService(
    val reviewerCommonService: ReviewerCommonService,

    val reviewEventLogRepository: ReviewEventLogRepository,
) {

    /**
     * 이벤트 로그를 DB에 저장하는 메소드
     * 리뷰를 작성한 작성자의 정보를 1차 캐시에서 가져와 점수를 변경
     *
     * @param reviewEventLogDto 로그를 저장하기 위해 사용되는 DTO
     * @param pointType 어떤 이유로 로그가 저장이 되는지 나타내는 정보
     */
    fun addReviewEventLog(
        reviewEventLogDto: ReviewEventLogDto,
        pointType: PointType
    ) {
        val eventLog = reviewEventLogDto.toEntity(pointType)
        eventLog.reviewer = reviewerCommonService.getReferenceReviewer(reviewEventLogDto.reviewerId)
        eventLog.reviewer.point += reviewEventLogDto.operatorType.value
        reviewEventLogRepository.save(eventLog)
    }

    /**
     * 이벤트 로그를 DB에 저장하는 메소드
     * 리뷰를 작성한 작성자의 정보를 1차 캐시에서 가져와 점수를 변경
     *
     * @param reviewEventLogDto 로그를 저장하기 위해 사용되는 DTO
     * @param operatorType 점수 증감에 대한 정보
     */
    fun addReviewEventLog(
        reviewEventLogDto: ReviewEventLogDto,
        operatorType: OperatorType
    ) {
        val eventLog = reviewEventLogDto.toEntity(operatorType)
        eventLog.reviewer = reviewerCommonService.getReferenceReviewer(reviewEventLogDto.reviewerId)
        eventLog.reviewer.point += operatorType.value
        reviewEventLogRepository.save(eventLog)
    }

}
