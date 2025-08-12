package com.study.splearn.splearn.adapter.security

import com.study.splearn.splearn.domain.member.PasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class SecurePasswordEncoder : PasswordEncoder {
    private val bCryptPasswordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()

    override fun encode(password: String): String {
        return bCryptPasswordEncoder.encode(password)
    }

    override fun matches(password: String, passwordHash: String): Boolean {
        return bCryptPasswordEncoder.matches(password, passwordHash)
    }
}
