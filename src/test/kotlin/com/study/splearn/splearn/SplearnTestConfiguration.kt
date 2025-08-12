package com.study.splearn.splearn

import com.study.splearn.splearn.application.member.required.EmailSender
import com.study.splearn.splearn.domain.member.MemberFixture
import com.study.splearn.splearn.domain.member.PasswordEncoder
import com.study.splearn.splearn.domain.shared.Email
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class SplearnTestConfiguration {

    @Bean
    fun emailSender(): EmailSender {
        return object : EmailSender {
            override fun send(
                email: Email,
                subject: String,
                body: String,
            ) {
                println("sending email: $email")
            }
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = MemberFixture.createPasswordEncoder()
}
