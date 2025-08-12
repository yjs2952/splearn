package com.study.splearn.splearn.domain.member

import com.study.splearn.splearn.domain.AbstractEntity
import com.study.splearn.splearn.domain.shared.Email
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.OneToOne
import org.hibernate.annotations.NaturalId
import org.springframework.util.Assert.state

@Entity
data class Member private constructor(

    @NaturalId
    val email: Email,
    var nickname: String,
    var passwordHash: String,

    @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val detail: MemberDetail,
) : AbstractEntity() {
    var status: MemberStatus = MemberStatus.PENDING

    fun activate() {
        state(status == MemberStatus.PENDING) { "PENDING 상태가 아니므로 활성화할 수 없습니다." }

        this.status = MemberStatus.ACTIVE
        this.detail.activate()
    }

    fun deactivate() {
        state(status == MemberStatus.ACTIVE) { "ACTIVE 상태가 아니므로 비활성화할 수 없습니다." }

        this.status = MemberStatus.DEACTIVATED
        this.detail.deactivate()
    }

    fun verifyPassword(password: String, passwordEncoder: PasswordEncoder): Boolean {
        return passwordEncoder.matches(password, this.passwordHash)
    }

    fun changeNickName(nickname: String) {
        this.nickname = nickname
    }

    fun updateInfo(updateRequest: MemberInfoUpdateRequest) {
        this.nickname = updateRequest.nickname
        this.detail.updateInfo(updateRequest)
    }

    fun changePassword(password: String, passwordEncoder: PasswordEncoder) {
        this.passwordHash = passwordEncoder.encode(password)
    }

    fun isActive(): Boolean {
        return this.status == MemberStatus.ACTIVE
    }

    companion object {
        fun register(
            registerRequest: MemberRegisterRequest,
            passwordEncoder: PasswordEncoder,
        ): Member {
            return Member(
                email = Email(registerRequest.email),
                nickname = registerRequest.nickname,
                passwordHash = passwordEncoder.encode(registerRequest.password),
                detail = MemberDetail.create()
            )
        }
    }
}
