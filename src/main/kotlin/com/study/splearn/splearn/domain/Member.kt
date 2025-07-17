package com.study.splearn.splearn.domain

data class Member private constructor(
    val email: Email,
    var nickname: String,
    var passwordHash: String,
) {
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
        fun create(
            createRequest: MemberCreateRequest,
            passwordEncoder: PasswordEncoder,
        ): Member {
            return Member(
                email = Email(createRequest.email),
                nickname = createRequest.nickname,
                passwordHash = passwordEncoder.encode(createRequest.password)
            )
        }
    }
}