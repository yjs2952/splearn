package com.study.splearn.splearn.domain

class DuplicateEmailException(
    message: String = "이미 사용 중인 이메일입니다.",
    cause: Throwable? = null,
) : RuntimeException(
    message,
    cause
)
