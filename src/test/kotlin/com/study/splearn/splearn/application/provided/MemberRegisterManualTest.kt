package com.study.splearn.splearn.application.provided

import com.study.splearn.splearn.application.MemberService
import com.study.splearn.splearn.application.required.EmailSender
import com.study.splearn.splearn.application.required.MemberRepository
import com.study.splearn.splearn.domain.Email
import com.study.splearn.splearn.domain.Member
import com.study.splearn.splearn.domain.MemberFixture
import com.study.splearn.splearn.domain.MemberStatus
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito
import org.springframework.test.util.ReflectionTestUtils

class MemberRegisterManualTest {

    @Test
    fun `registerTestStub`() {
        val register = MemberService(
            memberRepository = MemberRepositoryStub(),
            emailSender = EmailSenderStub(),
            passwordEncoder = MemberFixture.createPasswordEncoder()
        )

        val member = register.register(MemberFixture.createMemberMemberRegisterRequest())

        Assertions.assertThat(member.id).isEqualTo(1L)
        Assertions.assertThat(member.status).isEqualTo(MemberStatus.PENDING)
    }

    @Test
    fun `registerTestMock`() {
        val emailSenderMock = EmailSenderMock()
        val register = MemberService(
            memberRepository = MemberRepositoryStub(),
            emailSender = emailSenderMock,
            passwordEncoder = MemberFixture.createPasswordEncoder()
        )

        val member = register.register(MemberFixture.createMemberMemberRegisterRequest())

        Assertions.assertThat(member.id).isEqualTo(1L)
        Assertions.assertThat(member.status).isEqualTo(MemberStatus.PENDING)

        Assertions.assertThat(emailSenderMock.tos).hasSize(1)
        Assertions.assertThat(emailSenderMock.tos[0]).isEqualTo(member.email)
    }

    @Test
    fun `registerTestMockito`() {
        val emailSenderMock = Mockito.mock(EmailSender::class.java)
        val register = MemberService(
            memberRepository = MemberRepositoryStub(),
            emailSender = emailSenderMock,
            passwordEncoder = MemberFixture.createPasswordEncoder()
        )

        val member = register.register(MemberFixture.createMemberMemberRegisterRequest())

        Assertions.assertThat(member.id).isEqualTo(1L)
        Assertions.assertThat(member.status).isEqualTo(MemberStatus.PENDING)

        Mockito.verify(emailSenderMock).send(eq(member.email), any(), any())
    }

    class MemberRepositoryStub : MemberRepository {
        override fun save(member: Member): Member {
            ReflectionTestUtils.setField(member, "id", 1L)
            return member
        }

        override fun findByEmail(email: Email): Member? = null
    }

    class EmailSenderStub : EmailSender {
        override fun send(email: Email, subject: String, body: String) {
        }
    }

    class EmailSenderMock : EmailSender {
        val tos: MutableList<Email> = mutableListOf()
        override fun send(email: Email, subject: String, body: String) {
            tos.add(email)
        }
    }
}
