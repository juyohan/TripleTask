//package com.example.triple.model
//
//import javax.persistence.*
//
//@Entity
//@Table(
//    name = "first_reviews",
//)
//data class FirstReview(
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id: Long = 0L,
//    val first: Boolean = false,
//
//    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "reviewer_id")
//    val reviewer: Reviewer,
//    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "review_log_id")
//    val reviewEventLog: ReviewEventLog,
//) {
//}
