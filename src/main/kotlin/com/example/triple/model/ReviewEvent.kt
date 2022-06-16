package com.example.triple.model

import com.example.triple.model.enumeration.EventType
import javax.persistence.*

@Entity
@Table(
    name = "review_events",
    indexes = [
        Index(name = "idx_review_events_user_id", columnList = "user_id", unique = false),
        Index(name = "idx_review_events_place_id", columnList = "place_id", unique = false),
    ]
)
data class ReviewEvent(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Enumerated(EnumType.STRING)
    val type: EventType = EventType.REVIEW,
    val points: Int = 0,
    @Column(name = "user_id")
    val userId: String = "",
    @Column(name = "place_id")
    val placeId: String = "",
) {
}
