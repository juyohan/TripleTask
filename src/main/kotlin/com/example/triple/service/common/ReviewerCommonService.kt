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
) {
    // Reviewer 객체를 통해 DB에 저장
    fun addReviewer(reviewer: Reviewer) = reviewerRepository.save(reviewer)

    /**
     * userId 의 값을 통해 일치하는 작성자를 찾음.
     * 만약 찾지 못했다면, DB에 존재하지 않기 떄문에 저장하고 DTO 로 변환하여 반환
     *
     * @param userId 사용자 id 의 정보
     *
     * @return 사용자 정보를 담은 DTO 객체
     */
    fun findOrCreateReviewer(userId: String): ReviewerDto = reviewerRepository.findByUserId(userId)
        .orElseGet { addReviewer(Reviewer(userId = userId, point = 0)) }.toDto()

    /**
     * userId 의 값을 통해 일치하는 작성자를 찾으면, DTO 로 변환하여 반환
     * 만약 찾지 못했다면, 예외처리를 통해 던짐.
     *
     * @param userId 사용자 id 의 정보
     *
     * @return 사용자 정보를 담은 DTO
     */
    fun findOneReviewer(userId: String): ReviewerDto = reviewerRepository.findByUserId(userId)
        .orElseThrow { throw BusinessException(Exceptions.REVIEWER_NOT_FOUND) }.toDto()

    /**
     * 연관관계 저장을 위해 select 를 해서 Entity 자체를 가져오기보단, 프록시의 값을 반환
     *
     * @param id 작성자의 PK 값
     *
     * @return Reviewer 프록시 객체를 반환
     */
    fun getReferenceReviewer(id: Long): Reviewer = reviewerRepository.getReferenceById(id)
}
