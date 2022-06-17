package com.example.triple.repository

import com.example.triple.model.ReviewPhoto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReviewPhotoRepository : JpaRepository<ReviewPhoto, Long> {
    fun findByPhotoId(photoId: String): Optional<ReviewPhoto>
}
