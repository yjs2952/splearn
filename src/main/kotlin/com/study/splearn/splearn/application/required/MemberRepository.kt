package com.study.splearn.splearn.application.required

import com.study.splearn.splearn.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 회원 정보를 저장하거나 조회한다
 */
interface MemberRepository: JpaRepository<Member, Long> {
    fun save(member: Member): Member
}