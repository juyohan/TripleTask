package com.example.triple.repository

import com.example.triple.model.ReviewEvent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReviewEventRepository : JpaRepository<ReviewEvent, Long> {
    fun findByPlaceId(placeId: String): Optional<ReviewEvent>
}
