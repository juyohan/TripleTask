package com.example.triple.repository

import com.example.triple.model.Reviewer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReviewerRepository : JpaRepository<Reviewer, Long> {
    fun findByUserId(userId: String): Optional<Reviewer>
}
