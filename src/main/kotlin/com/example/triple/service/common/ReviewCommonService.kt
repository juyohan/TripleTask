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
    /**
     * placeId 의 장소에 리뷰가 몇개 달렸는지 갯수를 반환
     *
     * @param placeId 장소의 id 값
     *
     * @return 해당 장소를 가지고 있는 컬럼의 개수
     */
    fun countReviewByPlaceId(placeId: String): Int = reviewRepository.countByPlaceId(placeId)

    /**
     * ReviewDto 를 통해 Reivewer 작성자의 Entity 에 접근한 뒤, DB에 저장
     *
     * @param reviewDto 리뷰의 정보를 담고있는 DTO 객체
     */
    fun addReviewDto(reviewDto: ReviewDto) {
        var review: Review = reviewDto.toEntity()
        review.reviewer = reviewerCommonService.getReferenceReviewer(reviewDto.reviewerId)
        reviewRepository.save(review)
    }

    /**
     *
     */
    fun findOneReview(reviewId: String): ReviewDto = reviewRepository.findByReviewId(reviewId)
        .orElseThrow { throw BusinessException(Exceptions.REVIEW_NOT_FOUND) }.toDto()

    // review Entity 에 접근하여 데이터를 변경
    fun updateOneReview(reviewDto: ReviewDto) {
        val review = reviewRepository.getReferenceById(reviewDto.id)
        review.photoCount = reviewDto.photoCount
        review.contentLength = reviewDto.contentLength
    }

    // Review 프록시 Entity 반환
    fun getReferenceReview(id: Long): Review = reviewRepository.getReferenceById(id)

    // 데이터를 삭제하는 메소드
    fun deleteOneReview(id: Long) = reviewRepository.delete(getReferenceReview(id))
}
