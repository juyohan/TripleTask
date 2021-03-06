package com.example.triple.model

import com.example.triple.dto.ReviewerDto

import org.hibernate.annotations.DynamicUpdate
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.Column
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.UniqueConstraint

@Entity
@Table(
    name = "reviewers",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_reviewer_user_id", columnNames = ["user_id"]),
    ]
)
@DynamicUpdate
class Reviewer(
    @Id @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0L,
    var point: Int = 0,
    @Column(name = "user_id")
    val userId: String = "",
) {
    fun toDto(): ReviewerDto =
        ReviewerDto(
            id = id,
            userId = userId,
            point = point,
        )
}
