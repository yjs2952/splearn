package com.study.splearn.splearn.application.provided

import com.study.splearn.splearn.domain.Member
import com.study.splearn.splearn.domain.MemberRegisterRequest
import jakarta.validation.Valid

/**
 * 회원 등록과 관련된 기능을 제공한다
 */
interface MemberRegister {
    /**
     * 회원을 등록한다
     *
     * @param registerRequest 회원 등록 요청 정보
     * @return 등록된 회원 정보
     */
    fun register(@Valid registerRequest: MemberRegisterRequest): Member

    /**
     * 회원 이메일 중복 여부를 확인한다
     *
     * @param email 회원 이메일
     * @return 중복 여부
     */
    fun isEmailDuplicated(email: String): Boolean

    /**
     * 회원 닉네임 중복 여부를 확인한다
     *
     * @param nickname 회원 닉네임
     * @return 중복 여부
     */
    fun isNicknameDuplicated(nickname: String): Boolean
}
