package com.example.triple.model.abstraction

import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist

@MappedSuperclass
abstract class Time {
    @Column(name = "create_at")
    var createAt: ZonedDateTime? = null

    @PrePersist
    fun prePersist() {
        this.createAt = ZonedDateTime.now()
    }
}
