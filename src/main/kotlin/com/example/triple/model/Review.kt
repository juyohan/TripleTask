package com.example.triple.model

import com.example.triple.dto.ReviewDto
import com.example.triple.model.abstraction.Time
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Column
import javax.persistence.Index
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.ManyToOne
import javax.persistence.JoinColumn

import javax.persistence.FetchType.LAZY
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.UniqueConstraint

@Entity
@Table(
    name = "reviews",
    indexes = [
        Index(name = "idx_reviews_place_id", columnList = "place_id", unique = false),
    ],
    uniqueConstraints = [
        UniqueConstraint(name = "uk_review_review_id", columnNames = ["review_id"])
    ]
)
@DynamicUpdate
class Review(
    @Id @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L,
    @Column(name = "review_id")
    val reviewId: String = "",
    @Column(name = "place_id")
    val placeId: String = "",
    @Column(name = "content_length")
    var contentLength: Int = 0,
    @Column(name = "photo_count")
    var photoCount: Int = 0,
    @Column(name = "is_first")
    val isFirst: Boolean = false,

    @ManyToOne(fetch = LAZY) @JoinColumn(name = "reviewer_id")
    var reviewer: Reviewer = Reviewer(),
) : Time() {
    fun toDto(): ReviewDto = ReviewDto(
        id = id,
        reviewId = reviewId,
        placeId = placeId,
        photoCount = photoCount,
        contentLength = contentLength,
        isFirst = isFirst,
    )
}
