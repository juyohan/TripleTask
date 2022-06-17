package com.example.triple.model

import javax.persistence.*

// 사용자의 정보와 몇점을 가지고 있는지
@Entity
@Table(
    name = "reviewers",
    indexes = [
        Index(name = "idx_unique_reviewers_user_id", columnList = "user_id", unique = true),
    ]
)
class Reviewer(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    var points: Int = 0,
    @Column(name = "user_id")
    val userId: String = "",
) {
}
