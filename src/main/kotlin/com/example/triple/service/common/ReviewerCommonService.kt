package com.example.triple.service.common

import com.example.triple.dto.ReviewerDto
import com.example.triple.exception.BusinessException
import com.example.triple.exception.Exceptions
import com.example.triple.model.Reviewer
import com.example.triple.repository.ReviewerRepository
import org.springframework.stereotype.Service

@Service
class ReviewerCommonService(
    val reviewerRepository: ReviewerRepository
){
    fun addReviewer(reviewer: Reviewer) = reviewerRepository.save(reviewer)

    fun addReviewerDto(reviewerDto: ReviewerDto) = reviewerRepository.save(reviewerDto.toEntity())

    fun findOrCreateReviewer(userId: String): ReviewerDto = reviewerRepository.findByUserId(userId)
        .orElseGet { addReviewer(Reviewer(userId = userId, points = 0)) }.toDto()

    fun findOneReviewer(userId: String): ReviewerDto = reviewerRepository.findByUserId(userId)
        .orElseThrow { throw BusinessException(Exceptions.REVIEWER_NOT_FOUND) }.toDto()

    fun getReferenceReviewer(id: Long): Reviewer = reviewerRepository.getReferenceById(id)

}
