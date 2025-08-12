package com.study.splearn.splearn.domain.member

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProfileTest {

    @Test
    fun `profile`() {
        Profile("brobro")
        Profile("brobro100")
        Profile("bro")
    }

    @Test
    fun `profile fail`() {
        assertThrows<IllegalArgumentException> {
            Profile("")
            Profile("brobrobrobrobrobrobro")
            Profile("B")
            Profile("프로필")
        }
    }

    @Test
    fun `url`() {
        val profile = Profile("brofallz")

        Assertions.assertThat(profile.url()).isEqualTo("@brofallz")
    }
}
