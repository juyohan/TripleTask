package com.example.triple.model

import com.example.triple.model.abstraction.Time
import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators.IntSequenceGenerator
import javax.persistence.*

@Entity
@Table(
    name = "reviews",
    indexes = [
        Index(name = "idx_unique_reviews_review_id", columnList = "review_id", unique = true),
        Index(name = "idx_reviews_place_id", columnList = "place_id", unique = false)
    ]
)
@JsonIdentityInfo(generator = IntSequenceGenerator::class, property = "id")
class Review(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(name = "review_id")
    val reviewId: String = "",
    @Column(name = "place_id")
    val placeId: String = "",
    var content: String = "",

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "reviewer_id")
    val reviewer: Reviewer,

    @OneToMany(mappedBy = "review")
    var photos: MutableList<ReviewPhoto> = mutableListOf()
) : Time() {

    internal fun addPhoto(reviewPhoto: ReviewPhoto) {
        photos.add(reviewPhoto)
        reviewPhoto.review = this
    }
}
