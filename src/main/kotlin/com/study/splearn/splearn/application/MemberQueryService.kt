package com.study.splearn.splearn.application

import com.study.splearn.splearn.application.provided.MemberFinder
import com.study.splearn.splearn.application.required.MemberRepository
import com.study.splearn.splearn.domain.Member
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated

@Service
@Transactional
@Validated
class MemberQueryService(
    private val memberRepository: MemberRepository,
) : MemberFinder {
    override fun find(memberId: Long): Member {
        return memberRepository.findById(memberId) ?: throw IllegalArgumentException("회원을 찾을 수 없습니다. id: $memberId")
    }
}
