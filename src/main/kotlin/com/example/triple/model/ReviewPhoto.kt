package com.example.triple.model

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import javax.persistence.*

@Entity
@Table(name = "review_photos")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator::class, property = "id")
class ReviewPhoto(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(name = "photo_id")
    val photoId: String = "",

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE]) @JoinColumn(name = "review_id")
    var review: Review
)
