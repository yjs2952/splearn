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
     * 회원을 활성화한다
     *
     * @param memberId 활성화할 회원의 ID
     * @return 활성화된 회원 정보
     */
    fun activate(memberId: Long): Member
}
