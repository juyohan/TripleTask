package com.example.triple.model

import com.example.triple.model.enumeration.OperatorType
import com.example.triple.model.enumeration.PointType
import com.example.triple.model.enumeration.ReviewActionType
import com.example.triple.model.abstraction.Time
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Enumerated
import javax.persistence.EnumType.STRING
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "event_logs")
class EventLog(
    @Id @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L,
    @Column(name = "action_type")
    @Enumerated(STRING)
    var actionType: ReviewActionType = ReviewActionType.ADD,
    @Column(name = "point_type")
    @Enumerated(STRING)
    var pointType: PointType = PointType.CONTENT,
    @Column(name = "operator_type")
    @Enumerated(STRING)
    var operatorType: OperatorType = OperatorType.MINUS,

    @ManyToOne(fetch = LAZY) @JoinColumn(name = "reviewer_id")
    var reviewer: Reviewer = Reviewer(),
) : Time()
