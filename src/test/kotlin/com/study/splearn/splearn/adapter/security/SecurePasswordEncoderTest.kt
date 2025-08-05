package com.study.splearn.splearn.adapter.security

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class SecurePasswordEncoderTest {

    @Test
    fun `securePasswordEncorder`() {
        val securePasswordEncoder = SecurePasswordEncoder()

        val passwordHash = securePasswordEncoder.encode("secret")

        Assertions.assertThat(securePasswordEncoder.matches("secret", passwordHash)).isTrue
    }
}
