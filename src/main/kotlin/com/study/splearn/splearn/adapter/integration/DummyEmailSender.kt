package com.study.splearn.splearn.adapter.integration

import com.study.splearn.splearn.application.required.EmailSender
import com.study.splearn.splearn.domain.Email
import org.springframework.context.annotation.Fallback
import org.springframework.stereotype.Component

@Component
@Fallback
class DummyEmailSender : EmailSender {
    override fun send(email: Email, subject: String, body: String) {
        println("DummyEmailSender send email: $email")
    }
}
