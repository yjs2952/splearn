package com.study.splearn.splearn.application.provided

import com.study.splearn.splearn.SplearnTestConfiguration
import com.study.splearn.splearn.domain.DuplicateEmailException
import com.study.splearn.splearn.domain.MemberFixture
import com.study.splearn.splearn.domain.MemberStatus
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration::class)
class MemberRegisterTest(
    private val memberRegister: MemberRegister,
) {

    @Test
    fun `register`() {
        val member = memberRegister.register(MemberFixture.createMemberMemberRegisterRequest())

        Assertions.assertThat(member.id).isNotNull
        Assertions.assertThat(member.status).isEqualTo(MemberStatus.PENDING)
    }

    @Test
    fun `duplicate email fail`() {
        val member = memberRegister.register(MemberFixture.createMemberMemberRegisterRequest())

        assertThrows<DuplicateEmailException> {
            memberRegister.register(MemberFixture.createMemberMemberRegisterRequest())
        }
    }
}
