package com.example.triple.repository

import com.example.triple.model.ReviewEventLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewEventLogRepository : JpaRepository<ReviewEventLog, Long> {
}
