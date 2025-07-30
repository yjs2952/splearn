package com.study.splearn.splearn.domain

import com.study.splearn.splearn.domain.MemberFixture.createMemberMemberRegisterRequest
import com.study.splearn.splearn.domain.MemberFixture.createPasswordEncoder
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
        passwordEncoder = createPasswordEncoder()
        member = Member.register(
            registerRequest = createMemberMemberRegisterRequest(),
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
        Assertions.assertThat(member.verifyPassword("verysecret", passwordEncoder)).isTrue
        Assertions.assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse
    }

    @Test
    fun `changeNickName`() {
        Assertions.assertThat(member.nickname).isEqualTo("charlie")

        member.changeNickName("charlie2")

        Assertions.assertThat(member.nickname).isEqualTo("charlie2")
    }

    @Test
    fun `changePassword`() {
        member.changePassword("verysecret2", passwordEncoder)
        assertThat(member.verifyPassword("verysecret2", passwordEncoder)).isTrue
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
            Member.register(
                registerRequest = createMemberMemberRegisterRequest("invalid-email"),
                passwordEncoder = passwordEncoder
            )
        }
    }
}
