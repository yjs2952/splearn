package com.study.splearn.splearn.domain.member

import com.study.splearn.splearn.domain.AbstractEntity
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import org.springframework.util.Assert
import java.time.LocalDateTime

@Entity
data class MemberDetail(
    @Embedded
    var profile: Profile,
    var introduction: String,
    var registeredAt: LocalDateTime,
    var activatedAt: LocalDateTime? = null,
    var deactivatedAt: LocalDateTime? = null,
) : AbstractEntity() {
    internal fun activate() {
        Assert.isTrue(activatedAt == null) { "이미 활성화된 상태입니다." }
        this.activatedAt = LocalDateTime.now()
    }

    fun deactivate() {
        Assert.isTrue(deactivatedAt == null) { "이미 비활성화된 상태입니다." }
        this.deactivatedAt = LocalDateTime.now()
    }

    fun updateInfo(updateRequest: MemberInfoUpdateRequest) {
        this.profile = Profile(updateRequest.profileAddress)
        this.introduction = updateRequest.introduction
    }

    companion object {
        internal fun create(): MemberDetail {
            return MemberDetail(
                profile = Profile("default"),
                introduction = "",
                registeredAt = LocalDateTime.now(),
            )
        }
    }
}
