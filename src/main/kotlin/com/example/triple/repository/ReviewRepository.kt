package com.example.triple.repository

import com.example.triple.model.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface ReviewRepository : JpaRepository<Review, Long> {
    fun findByReviewId(reviewId: String): Optional<Review>
    fun countByPlaceId(placeId: String): Int
}
