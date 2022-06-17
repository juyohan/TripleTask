package com.example.triple.service

import com.example.triple.dto.EventDto
import com.example.triple.model.Review
import com.example.triple.model.ReviewEventLog
import com.example.triple.model.ReviewPhoto
import com.example.triple.model.Reviewer
import com.example.triple.model.enumeration.PointType
import com.example.triple.model.enumeration.ReviewActionType
import com.example.triple.repository.ReviewEventLogRepository
import com.example.triple.repository.ReviewPhotoRepository
import com.example.triple.repository.ReviewRepository
import com.example.triple.repository.ReviewerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 1. 리뷰 내용이 0자 이상일 때
 * 2. 리뷰 첨부 사진이 1개 이상일 때
 * 3. 장소에 첫 리뷰를 작성했을 때
 */
@Service
class ReviewEventRegisterService(
    val reviewEventLogRepository: ReviewEventLogRepository,
    val reviewerRepository: ReviewerRepository,
    val reviewRepository: ReviewRepository,
    val reviewPhotoRepository: ReviewPhotoRepository,
) {

    /**
     * 이벤트 리뷰와 조건에 만족할 때마다 로그 DB에 저장하고 점수를 반영
     *
     * 조건 - 1. 리뷰 댓글의 내용이 1자 이상의 텍스트 작성일 때
     *       2. 리뷰에 사진을 1장 이상 첨부했을 때
     *       3. 해당 장소에 처음으로 리뷰를 작성했을 때
     *
     * 각 조건을 만족했을 때, 1점씩 부여하여 마지막에 점수를 반영시킴
     *
     * @param eventDto 요청받은 정보의 Dto
     */
    @Transactional
    fun addReviewEvent(eventDto: EventDto.Request.EventDetails) {
        val reviewer = findOrAddReviewer(eventDto.userId)

        val points = addContentReviewLog(eventDto.content, reviewer) +
                addPhotoReviewLog(eventDto.attachedPhotoIds, reviewer) + addFirstPlaceReviewLog(eventDto, reviewer)

        modifyReviewerPoints(points, reviewer)
    }

    /**
     * 작성자의 점수를 수정하는 메소드
     *
     * @param points 3가지의 조건에 맞는 점수
     * @param reviewer 점수를 저장할 작성자
     */
    fun modifyReviewerPoints(points: Int, reviewer: Reviewer) = points.also { reviewer.points = it }


    /**
     * 리뷰의 작성자가 존재하는지 확인하고, 없다면 DB에 저장
     *
     * @param userId 작성자의 id
     *
     * @return 작성자의 정보를 담은 Entity
     */
    fun findOrAddReviewer(userId: String): Reviewer = reviewerRepository.findByUserId(userId).orElseGet {
        reviewerRepository.save(
            Reviewer(
                points = 0,
                userId = userId
            )
        )
    }

    /**
     * 리뷰에 대한 정보를 DB에 저장
     *
     * @param reviewId 리뷰의 id
     * @param content 리뷰의 내용
     * @param placeId 리뷰를 적는 장소의 id
     * @param reviewer 리뷰를 적은 작성자의 Entity
     *
     * @return 저장한 리뷰의 Entity
     */
    fun addReview(
        reviewId: String, content: String, placeId: String, reviewer: Reviewer
    ): Review = reviewRepository.save(
        Review(
            reviewId = reviewId, content = content, placeId = placeId, reviewer = reviewer
        )
    )


    /**
     * 리뷰에 대한 사진들을 DB에 저장
     *
     * @param photos 사진의 이름이 담긴 리스트
     * @param review 사진이 담긴 리뷰 Entity
     */
    fun addReviewPhoto(
        photos: List<String>, review: Review
    ) = photos.stream().forEach { photoId ->
        reviewPhotoRepository.save(
            ReviewPhoto(
                photoId = photoId,
                review = review
            )
        )
    }


    /**
     * 리뷰 내용의 길이를 확인하여 조건에 만족하면 log 에 저장시키고 아니라면, 저장하지 않음.
     *
     * @param content 리뷰 내용
     * @param reviewer 리뷰를 작성한 작성자의 Entity
     *
     * @return
     * apply 변수가 Null 이라면 0, 아니라면 1을 반환
     */
    fun addContentReviewLog(content: String, reviewer: Reviewer): Int {
        val apply = content.takeIf { content.isNotEmpty() }?.apply {
            reviewEventLogRepository.save(
                ReviewEventLog(
                    actionType = ReviewActionType.ADD, pointType = PointType.CONTENT, reviewer = reviewer
                )
            )
        }
        return if (apply.isNullOrEmpty()) 0 else 1
    }


    /**
     * photos 의 값이 비어있지 않거나 Null 값이 아니라면, log 를 저장함
     *
     * @param photos 여러개의 이미지 id
     * @param reviewer 리뷰를 작성한 작성자의 Entity
     *
     * @return
     * apply 의 값이 비어있거나 Null 이라면, 0을 반환하고 아니라면, 1을 반환
     */
    fun addPhotoReviewLog(photos: List<String>, reviewer: Reviewer): Int {
        val apply = photos.takeUnless { it.isNullOrEmpty() }?.apply {
            reviewEventLogRepository.save(
                ReviewEventLog(
                    actionType = ReviewActionType.ADD, pointType = PointType.PHOTO, reviewer = reviewer
                )
            )
        }
        return if (apply?.isNullOrEmpty() == true) 0 else 1
    }


    /**
     * 해당 장소에 리뷰가 이미 존재하는지 확인하는 메소드
     * 장소에 대한 리뷰가 비어있다면, 1번재로 리뷰를 작성하는 것이므로 log 를 저장
     * 그리고 리뷰의 정보를 저장하고 만약 사진이 존재한다면, 사진에 대한 정보도 저장
     *
     * @param eventDto 담겨온 요청에 대한 정보 Dto
     * @param reviewer 리뷰를 작성한 작성자의 Entity
     *
     * @return
     * apply 의 값이 비어있다면, 1번째 리뷰이므로 가산점을 주기위해 1을 반환하고, 비어있지 않다면, 1번째 리뷰가 아니기 때문에 0을 반환
     */
    fun addFirstPlaceReviewLog(eventDto: EventDto.Request.EventDetails, reviewer: Reviewer): Int {
        val apply = reviewRepository.findByPlaceId(eventDto.placeId).takeIf { it.isEmpty }?.apply {
            reviewEventLogRepository.save(
                ReviewEventLog(
                    actionType = ReviewActionType.ADD, pointType = PointType.FIRST, reviewer = reviewer
                )
            )
        }

        val review = addReview(
            reviewId = eventDto.reviewId,
            content = eventDto.content,
            placeId = eventDto.placeId,
            reviewer = reviewer
        )

        eventDto.attachedPhotoIds.takeIf { it.isNotEmpty() }?.apply {
            addReviewPhoto(
                eventDto.attachedPhotoIds,
                review,
            )
        }
        return if (apply?.isEmpty == true || apply == null) 1 else 0
    }

}
