package com.study.splearn.splearn.application.provided

import com.study.splearn.splearn.SplearnTestConfiguration
import com.study.splearn.splearn.application.member.provided.MemberFinder
import com.study.splearn.splearn.application.member.provided.MemberRegister
import com.study.splearn.splearn.domain.member.MemberFixture
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
@Import(value = [SplearnTestConfiguration::class])
class MemberFinderTest(
    private val memberFinder: MemberFinder,
    private val memberRegister: MemberRegister,
    private val entityManager: EntityManager,
) {

    @Test
    fun `find`() {
        val member = memberRegister.register(MemberFixture.createMemberMemberRegisterRequest())
        entityManager.flush()
        entityManager.clear()

        val found = memberFinder.find(member.id!!)

        Assertions.assertThat(member.id).isEqualTo(found.id)
    }

    @Test
    fun `findFail`() {
        assertThrows<IllegalArgumentException> {
            memberFinder.find(999L)
        }
    }
}
