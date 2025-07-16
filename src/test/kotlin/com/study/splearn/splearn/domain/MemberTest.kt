package com.study.splearn.splearn.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MemberTest {

    @Test
    fun `Member 를 생성한다`() {
        Member("bro.fallz@kakaocorp.com", "bro.fallz", "secret")
            .also {
                assertThat(it.status).isEqualTo(MemberStatus.PENDING)
            }
    }

    @Test
    fun `activate 메롱이다`() {
        val member = Member("bro.fallz@kakaocorp.com", "bro.fallz", "secret")
        member.activate()

        assertThat(member.status).isEqualTo(MemberStatus.ACTIVE)
    }

    @Test
    fun `activate 실패`() {
        val member = Member("bro.fallz@kakaocorp.com", "bro.fallz", "secret")
        member.activate()

        assertThrows<IllegalStateException> {
            member.activate()
        }
    }

    @Test
    fun `deactivate 성공`() {
        val member = Member("bro.fallz@kakaocorp.com", "bro.fallz", "secret")
        member.activate()

        member.deactivate()

        assertThat(member.status).isEqualTo(MemberStatus.DEACTIVATED)
    }

    @Test
    fun `deactivate 실패`() {
        val member = Member("bro.fallz@kakaocorp.com", "bro.fallz", "secret")

        assertThrows<IllegalStateException> {
            member.deactivate()
        }

        member.activate()
        member.deactivate()

        assertThrows<IllegalStateException> {
            member.deactivate()
        }
    }
}