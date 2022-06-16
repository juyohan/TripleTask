package com.example.triple.service

import com.example.triple.dto.EventDto
import com.example.triple.repository.ReviewEventRepository
import org.springframework.stereotype.Service

// 리뷰 이벤트 등록 서비
@Service
class ReviewEventRegisterService(
    val reviewEventRepository: ReviewEventRepository,
) {

    /**
     * 이벤트 리뷰 DB에 저장
     * @param eventDto 외부에서 넘어온 데이터
     *
     */
    fun addReviewEvent(eventDto: EventDto.Request.EventDetails) {
        var points: Int = 0
        val contents = isEmptyContents(eventDto.content)
        val photos = isEmptyPhotos(eventDto.attachedPhotoIds)
        val review = checkFirstReview(eventDto.placeId)
    }

    /**
     * 리뷰 내용의 길이 확인
     * @param content 리뷰 내용
     * @return 리뷰 내용이 비어있지 않다면, 1점을 반환하고 아니라면, 0점을 반환
     */
    fun isEmptyContents(content: String): Int = if (content.isNotEmpty()) 1 else 0

    /**
     * 사진 첨부 유무 확인
     * @param photos 여러개의 이미지의 id
     * @return 첨부된 사진이 1개라도 있다면, 1점을 반환하고 아니라면, 0점을 반환
     */
    fun isEmptyPhotos(photos: List<String>): Int = if (photos.isNotEmpty()) 1 else 0

    /**
     * 해당 장소에 리뷰 유무 확인
     * @param placeId 리뷰를 작성한 장소의 id
     * @return 리뷰를 작성한 장소에 값이 비어있다면, 처음 작성한 것이므로 1점을 반환하고 아니라면, 0점을 반환
     */
    fun checkFirstReview(placeId: String): Int = if (reviewEventRepository.findByPlaceId(placeId).isEmpty) 1 else 0
}
