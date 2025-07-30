package com.study.splearn.splearn.application.provided

import com.study.splearn.splearn.domain.Member

/**
 * 회원을 조회한다
 */
interface MemberFinder {
    fun find(memberId: Long): Member
}
