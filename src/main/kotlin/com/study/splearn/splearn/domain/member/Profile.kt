package com.study.splearn.splearn.domain.member

import jakarta.persistence.Embeddable
import java.util.regex.Pattern

@Embeddable
data class Profile(
    val address: String,
) {

    fun url(): String {
        return "@$address"
    }

    companion object {
        private val PROFILE_ADDRESS_PATTERN = Pattern.compile("[a-z0-9]+")
    }

    init {
        if (!PROFILE_ADDRESS_PATTERN.matcher(address).matches()) {
            throw IllegalArgumentException("프로필 주소 형식이 바르지 않습니다: [$address]")
        }

        if (address.length > 15) {
            throw IllegalArgumentException("프로필 주소는 15자 이하 이어야 합니다: [$address]")
        }
    }
}
