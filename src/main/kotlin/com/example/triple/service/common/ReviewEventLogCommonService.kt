package com.example.triple.service.common

import com.example.triple.dto.ReviewEventLogDto
import com.example.triple.model.enumeration.OperatorType
import com.example.triple.model.enumeration.PointType
import com.example.triple.repository.ReviewEventLogRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ReviewEventLogCommonService(
    val reviewerCommonService: ReviewerCommonService,

    val reviewEventLogRepository: ReviewEventLogRepository,
) {
    fun addReviewEventLog(
        reviewEventLogDto: ReviewEventLogDto,
        pointType: PointType
    ) {
        val eventLog = reviewEventLogDto.toEntity(pointType)
        eventLog.reviewer = reviewerCommonService.getReferenceReviewer(reviewEventLogDto.reviewerId)
        eventLog.reviewer.point += reviewEventLogDto.operatorType.value
        reviewEventLogRepository.save(eventLog)
    }

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
