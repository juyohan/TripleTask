package com.example.triple.model

import com.example.triple.model.enumeration.ReviewActionType
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(
    name = "review_event_log",
    indexes = [
        Index(name = "unique_idx_review_event_log_action_type", columnList = "action_type", unique = true),
        Index(name = "unique_idx_review_event_log_review_event", columnList = "review_event", unique = true)
    ]
)
data class ReviewEventLog(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(name = "action_type")
    val actionType: ReviewActionType = ReviewActionType.ADD,
    @Column(name = "created_at")
    var createdAt: ZonedDateTime,

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "id")
    @Column(name = "review_event")
    val reviewEvent: ReviewEvent,
){
    @PrePersist
    fun prePersist() {
        createdAt = ZonedDateTime.now()
    }
}
