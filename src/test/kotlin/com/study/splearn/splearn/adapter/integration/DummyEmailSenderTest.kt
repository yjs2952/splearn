package com.study.splearn.splearn.adapter.integration

import com.study.splearn.splearn.domain.Email
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junitpioneer.jupiter.StdIo
import org.junitpioneer.jupiter.StdOut

class DummyEmailSenderTest {

    @Test
    @StdIo
    fun `dummy mail sender`(out: StdOut) {
        val dummyEmailSender = DummyEmailSender()
        dummyEmailSender.send(Email("bro@splearn.app"), "subject", "body")

        Assertions.assertThat(out.capturedLines()[0])
            .isEqualTo("DummyEmailSender send email: Email(address=bro@splearn.app)")
    }
}
