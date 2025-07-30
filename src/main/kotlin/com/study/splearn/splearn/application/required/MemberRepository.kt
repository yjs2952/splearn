package com.study.splearn.splearn.application.required

import com.study.splearn.splearn.domain.Email
import com.study.splearn.splearn.domain.Member
import org.springframework.data.repository.Repository

/**
 * 회원 정보를 저장하거나 조회한다
 */
interface MemberRepository: Repository<Member, Long> {
    fun save(member: Member): Member
    fun findByEmail(email: Email): Member?
    fun findById(id: Long): Member?
}
