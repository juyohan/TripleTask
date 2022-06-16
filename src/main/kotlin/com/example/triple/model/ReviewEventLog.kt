package com.example.triple.model

import com.example.triple.model.enumeration.ReviewActionType
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(
    name = "review_event_logs",
    indexes = [
        Index(name = "idx_review_event_logs_action_type", columnList = "action_type", unique = false),
        Index(name = "idx_review_event_logs_review_event", columnList = "review_event_id", unique = false)
    ]
)
data class ReviewEventLog(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(name = "action_type")
    @Enumerated(EnumType.STRING)
    val actionType: ReviewActionType = ReviewActionType.ADD,
    @Column(name = "created_at")
    var createdAt: ZonedDateTime,

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "id")
    @Column(name = "review_event_id")
    val reviewEvent: ReviewEvent,
){
    @PrePersist
    fun prePersist() {
        createdAt = ZonedDateTime.now()
    }
}
