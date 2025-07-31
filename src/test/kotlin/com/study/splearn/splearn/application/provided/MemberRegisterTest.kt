package com.study.splearn.splearn.application.provided

import com.study.splearn.splearn.SplearnTestConfiguration
import com.study.splearn.splearn.domain.DuplicateEmailException
import com.study.splearn.splearn.domain.MemberFixture
import com.study.splearn.splearn.domain.MemberRegisterRequest
import com.study.splearn.splearn.domain.MemberStatus
import jakarta.persistence.EntityManager
import jakarta.validation.ConstraintViolationException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration::class)
class MemberRegisterTest(
    private val memberRegister: MemberRegister,
    private val entityManager: EntityManager,
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

    @Test
    fun `activate`() {
        val member = memberRegister.register(MemberFixture.createMemberMemberRegisterRequest())
        entityManager.flush()
        entityManager.clear()

        val activatedMember = memberRegister.activate(member.id!!)
        entityManager.flush()

        Assertions.assertThat(activatedMember.id).isEqualTo(member.id)
        Assertions.assertThat(activatedMember.status).isEqualTo(MemberStatus.ACTIVE)
    }

    @Test
    fun `member register request fail`() {
        checkValidation(MemberRegisterRequest("bro@splearn.app", "bro", "secret"))
        checkValidation(MemberRegisterRequest("bro@splearn.app", "bro--------------", "secret"))
        checkValidation(MemberRegisterRequest("brosplearn.app", "bro", "secret"))
    }

    private fun checkValidation(invalid: MemberRegisterRequest) {
        assertThrows<ConstraintViolationException> {
            memberRegister.register(invalid)
        }
    }
}
