package com.study.splearn.splearn.application.required

import com.study.splearn.splearn.domain.Email
import org.springframework.stereotype.Component

/**
 * 이메일을 발송한다.
 */
@Component
interface EmailSender {
    fun send(email: Email, subject: String, body: String)
}
