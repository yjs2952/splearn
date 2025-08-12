package com.study.splearn.splearn.application.required

import com.study.splearn.splearn.application.member.required.MemberRepository
import com.study.splearn.splearn.domain.member.Member
import com.study.splearn.splearn.domain.member.MemberFixture
import com.study.splearn.splearn.domain.member.MemberStatus
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.dao.DataIntegrityViolationException

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun `createMember`() {
        val member = Member.Companion.register(
            MemberFixture.createMemberMemberRegisterRequest(),
            MemberFixture.createPasswordEncoder()
        )

        Assertions.assertThat(member.id).isNull()

        memberRepository.save(member)
        entityManager.flush()
        entityManager.clear()

        val found = memberRepository.findById(member.id!!)
        Assertions.assertThat(found?.status).isEqualTo(MemberStatus.PENDING)
        Assertions.assertThat(found?.detail?.registeredAt).isNotNull

        Assertions.assertThat(member.id).isNotNull()
    }

    @Test
    fun `duplicateEmailFail`() {
        val member = Member.Companion.register(
            MemberFixture.createMemberMemberRegisterRequest(),
            MemberFixture.createPasswordEncoder()
        )
        memberRepository.save(member)

        val member2 = Member.Companion.register(
            MemberFixture.createMemberMemberRegisterRequest(),
            MemberFixture.createPasswordEncoder()
        )

        assertThrows<DataIntegrityViolationException> {
            memberRepository.save(member2)
        }
    }
}
