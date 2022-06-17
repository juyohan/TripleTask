package com.example.triple.model

import com.example.triple.model.abstraction.Time
import com.example.triple.model.enumeration.PointType
import com.example.triple.model.enumeration.ReviewActionType
import javax.persistence.*


// 점수가 움직일 때마다 log에 담아서 저장
@Entity
@Table(name = "review_event_logs")
data class ReviewEventLog (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(name = "action_type")
    @Enumerated(EnumType.STRING)
    val actionType: ReviewActionType = ReviewActionType.ADD,
    @Column(name = "point_type")
    @Enumerated(EnumType.STRING)
    val pointType: PointType = PointType.CONTENT,

//    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "review_id")
//    val review: Review,

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "reviewer_id")
    val reviewer: Reviewer,

) : Time(){

}
