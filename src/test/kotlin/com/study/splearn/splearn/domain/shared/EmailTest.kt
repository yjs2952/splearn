package com.study.splearn.splearn.domain.shared

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class EmailTest {

    @Test
    fun `equality`() {
        val email1 = Email("bro@splearn.com")
        val email2 = Email("bro@splearn.com")

        Assertions.assertThat(email1).isEqualTo(email2)
    }

}
