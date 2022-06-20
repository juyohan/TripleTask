package com.example.triple.dto

import com.example.triple.model.Review

data class ReviewDto(
    val id: Long = 0L,
    val reviewId: String = "",
    val placeId: String = "",
    var reviewerId: Long = 0L,
    var photoCount: Int = 0,
    var contentLength: Int = 0,
    var isFirst: Boolean = false,
) {
    fun createDto(eventDto: EventDto, reviewerId: Long): ReviewDto = ReviewDto(
        placeId = eventDto.placeId,
        photoCount = eventDto.attachedPhotoIds.size,
        contentLength = eventDto.content.length,
        reviewId = eventDto.reviewId,
        reviewerId = reviewerId,
    )

    fun toEntity(): Review =
        Review(
            reviewId = reviewId,
            placeId = placeId,
            contentLength = contentLength,
            photoCount = photoCount,
            isFirst = isFirst,
        )
}
