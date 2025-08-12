package com.study.splearn.splearn.application.member.provided

import com.study.splearn.splearn.domain.member.Member

/**
 * 회원을 조회한다
 */
interface MemberFinder {
    fun find(memberId: Long): Member
}
