package com.study.splearn.splearn.domain

import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.NaturalIdCache

@Entity
@Table(
    name = "member", uniqueConstraints = [
        UniqueConstraint(name = "UK_MEMBER_EMAIL_ADDRESS", columnNames = ["email_address"]),
    ]
)
@NaturalIdCache
data class Member private constructor(

    @Embedded
    @NaturalId
    val email: Email,

    @Column(length = 100, nullable = false)
    var nickname: String,

    @Column(length = 200, nullable = false)
    var passwordHash: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    var status: MemberStatus = MemberStatus.PENDING

    fun activate() {
        check(status == MemberStatus.PENDING) { "PENDING 상태가 아니므로 활성화할 수 없습니다." }

        this.status = MemberStatus.ACTIVE
    }

    fun deactivate() {
        check(status == MemberStatus.ACTIVE) { "ACTIVE 상태가 아니므로 비활성화할 수 없습니다." }

        this.status = MemberStatus.DEACTIVATED
    }

    fun verifyPassword(password: String, passwordEncoder: PasswordEncoder): Boolean {
        return passwordEncoder.matches(password, this.passwordHash)
    }

    fun changeNickName(nickname: String) {
        this.nickname = nickname
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
                passwordHash = passwordEncoder.encode(registerRequest.password)
            )
        }
    }
}
