package com.study.splearn.splearn.domain

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MemberTest {

    private lateinit var member: Member
    private lateinit var passwordEncoder: PasswordEncoder

    @BeforeEach
    fun setUp() {
        passwordEncoder = object : PasswordEncoder {
            override fun encode(password: String): String {
                return password.uppercase() // 단순한 예시로 비밀번호를 뒤집어서 저장
            }

            override fun matches(password: String, passwordHash: String): Boolean {
                return encode(password) == passwordHash // 단순한 예시로 비밀번호가 뒤집힌 것과 비교
            }
        }
        member = Member.create(
            createRequest = MemberCreateRequest(
                email = "bro.fallz@kakaocorp.com",
                nickname = "bro.fallz",
                password = "secret"
            ),
            passwordEncoder = passwordEncoder
        )
    }

    @Test
    fun `Member 를 생성한다`() {
        member
            .also {
                assertThat(it.status).isEqualTo(MemberStatus.PENDING)
            }
    }

    @Test
    fun `activate 메롱이다`() {
        member.activate()

        assertThat(member.status).isEqualTo(MemberStatus.ACTIVE)
    }

    @Test
    fun `activate 실패`() {
        member.activate()

        assertThrows<IllegalStateException> {
            member.activate()
        }
    }

    @Test
    fun `deactivate 성공`() {
        member.activate()

        member.deactivate()

        assertThat(member.status).isEqualTo(MemberStatus.DEACTIVATED)
    }

    @Test
    fun `deactivate 실패`() {
        assertThrows<IllegalStateException> {
            member.deactivate()
        }

        member.activate()
        member.deactivate()

        assertThrows<IllegalStateException> {
            member.deactivate()
        }
    }

    @Test
    fun `verifyPassword`() {
        Assertions.assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue
        Assertions.assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse
    }

    @Test
    fun `changeNickName`() {
        Assertions.assertThat(member.nickname).isEqualTo("bro.fallz")

        member.changeNickName("batman")

        Assertions.assertThat(member.nickname).isEqualTo("batman")
    }

    @Test
    fun `changePassword`() {
        member.changePassword("supersecret", passwordEncoder)
        assertThat(member.verifyPassword("supersecret", passwordEncoder)).isTrue
    }

    @Test
    fun `shouldBeActive`() {
        Assertions.assertThat(member.isActive()).isFalse

        member.activate()

        Assertions.assertThat(member.isActive()).isTrue

        member.deactivate()

        Assertions.assertThat(member.isActive()).isFalse
    }

    @Test
    fun `invalidEmail`() {
        assertThrows<IllegalArgumentException> {
            Member.create(
                createRequest = MemberCreateRequest(
                    email = "invalid-email",
                    nickname = "nickname",
                    password = "password"
                ),
                passwordEncoder = passwordEncoder
            )
        }
    }
}