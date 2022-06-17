package com.example.triple.model

import javax.persistence.*

@Entity
@Table(name = "review_photos")
data class ReviewPhoto(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(name = "photo_id")
    val photoId: String = "",

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE, CascadeType.REFRESH]) @JoinColumn(name = "review_id")
    val review: Review
)
