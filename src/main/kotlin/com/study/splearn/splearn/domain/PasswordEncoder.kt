package com.study.splearn.splearn.domain

interface PasswordEncoder {
    fun encode(password: String): String

    fun matches(password: String, passwordHash: String): Boolean
}