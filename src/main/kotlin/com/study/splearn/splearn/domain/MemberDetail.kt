package com.study.splearn.splearn.domain

import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity
data class MemberDetail(

    val profile: String,
    var introduction: String,
    var registeredAt: LocalDateTime,
    val activatedAt: LocalDateTime? = null,
    val deactivatedAt: LocalDateTime? = null,
) : AbstractEntity() {

}
