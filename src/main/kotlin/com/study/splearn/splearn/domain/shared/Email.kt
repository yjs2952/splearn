package com.study.splearn.splearn.domain.shared

import jakarta.persistence.Embeddable
import java.util.regex.Pattern

@Embeddable
data class Email(
    val address: String,
) {

    init {
        require(EMAIL_PATTERN.matcher(address).matches()) { "유효하지 않은 이메일 형식입니다: $address" }
    }

    companion object {
        private val EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    }
}
