package com.study.splearn.splearn.domain.member

object MemberFixture {

    fun createMemberMemberRegisterRequest(email: String): MemberRegisterRequest = MemberRegisterRequest(
        email = email,
        nickname = "charlie",
        password = "verysecret"
    )

    fun createMemberMemberRegisterRequest(): MemberRegisterRequest = createMemberMemberRegisterRequest("bro@splearn.com")

    fun createPasswordEncoder(): PasswordEncoder = object : PasswordEncoder {
        override fun encode(password: String): String {
            return password.uppercase() // 단순한 예시로 비밀번호를 뒤집어서 저장
        }

        override fun matches(password: String, passwordHash: String): Boolean {
            return encode(password) == passwordHash // 단순한 예시로 비밀번호가 뒤집힌 것과 비교
        }
    }
}
