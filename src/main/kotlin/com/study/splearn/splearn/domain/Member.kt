package com.study.splearn.splearn.domain

data class Member private constructor(
    val email: String,
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
        this.passwordHash = passwordEncoder.encode(password) // 비밀번호 해시를 변경하는 로직이 필요합니다.
        // 예를 들어, PasswordEncoder를 사용하여 새 비밀번호를 해시할 수 있습니다.
    }

    companion object {
        fun create(
            email: String,
            nickname: String,
            password: String,
            passwordEncoder: PasswordEncoder,
        ): Member {
            return Member(email, nickname, passwordEncoder.encode(password))
        }
    }
}