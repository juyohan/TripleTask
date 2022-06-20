package com.example.triple.service.common

import com.example.triple.dto.ReviewDto
import com.example.triple.exception.BusinessException
import com.example.triple.exception.Exceptions
import com.example.triple.model.Review
import com.example.triple.repository.ReviewRepository
import org.springframework.stereotype.Service

@Service
class ReviewCommonService(
    val reviewerCommonService: ReviewerCommonService,

    val reviewRepository: ReviewRepository,
) {
    fun countReviewByPlaceId(placeId: String): Int = reviewRepository.countByPlaceId(placeId)

    fun addReviewDto(reviewDto: ReviewDto) {
        var review: Review = reviewDto.toEntity()
        review.reviewer = reviewerCommonService.getReferenceReviewer(reviewDto.reviewerId)
        reviewRepository.save(review)
    }

    fun findOneReview(reviewId: String): ReviewDto = reviewRepository.findByReviewId(reviewId)
        .orElseThrow { throw BusinessException(Exceptions.REVIEW_NOT_FOUND)}.toDto()

    fun updateOneReview(reviewDto: ReviewDto) {
        val review = reviewRepository.getReferenceById(reviewDto.id)
        review.photoCount = reviewDto.photoCount
        review.contentLength = reviewDto.contentLength
    }

    fun deleteOneReview(reviewId: String) = reviewRepository.deleteByReviewId(reviewId)
}
