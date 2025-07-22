package com.study.splearn.splearn.application.provided

import com.study.splearn.splearn.application.required.MemberRepository
import com.study.splearn.splearn.domain.Member
import com.study.splearn.splearn.domain.MemberFixture.createMemberMemberRegisterRequest
import com.study.splearn.splearn.domain.MemberFixture.createPasswordEncoder
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Autowired
    lateinit var entityManager: EntityManager

    @Test
    fun `createMember`() {
        val member = Member.register(createMemberMemberRegisterRequest(), createPasswordEncoder())

        Assertions.assertThat(member.id).isNull()

        memberRepository.save(member)
        entityManager.flush()

        Assertions.assertThat(member.id).isNotNull()
    }
}