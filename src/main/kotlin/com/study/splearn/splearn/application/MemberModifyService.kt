package com.study.splearn.splearn.application

import com.study.splearn.splearn.application.provided.MemberFinder
import com.study.splearn.splearn.application.provided.MemberRegister
import com.study.splearn.splearn.application.required.EmailSender
import com.study.splearn.splearn.application.required.MemberRepository
import com.study.splearn.splearn.domain.DuplicateEmailException
import com.study.splearn.splearn.domain.Email
import com.study.splearn.splearn.domain.Member
import com.study.splearn.splearn.domain.MemberRegisterRequest
import com.study.splearn.splearn.domain.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.annotation.Validated

@Service
@Transactional
@Validated
class MemberModifyService(
    private val memberFinder: MemberFinder,
    private val memberRepository: MemberRepository,
    private val emailSender: EmailSender,
    private val passwordEncoder: PasswordEncoder,
) : MemberRegister {

    override fun register(registerRequest: MemberRegisterRequest): Member {
        checkDuplicateEmail(registerRequest)

        val member = Member.register(registerRequest, passwordEncoder)
        memberRepository.save(member)

        sendWelcomeEmail(member)
        return member
    }

    override fun activate(memberId: Long): Member {
        // 회원 조회 및 예외처리 코드를 memberFinder를 통해 수행
        val member = memberFinder.find(memberId)

        // 도메인 모델에 만들어 놓은 기능을 수행
        member.activate()
        return memberRepository.save(member)
    }

    private fun sendWelcomeEmail(member: Member) {
        emailSender.send(member.email, "등록을 완료해주세요.", "아래 링크를 클릭해서 등록을 완료해주세요.")
    }

    private fun checkDuplicateEmail(registerRequest: MemberRegisterRequest) {
        memberRepository.findByEmail(Email(registerRequest.email))?.let {
            throw DuplicateEmailException("이미 등록된 이메일입니다: ${registerRequest.email}")
        }
    }
}
