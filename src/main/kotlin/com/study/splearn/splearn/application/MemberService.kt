package com.study.splearn.splearn.application

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
class MemberService(
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

    private fun sendWelcomeEmail(member: Member) {
        emailSender.send(member.email, "등록을 완료해주세요.", "아래 링크를 클릭해서 등록을 완료해주세요.")
    }

    private fun checkDuplicateEmail(registerRequest: MemberRegisterRequest) {
        memberRepository.findByEmail(Email(registerRequest.email))?.let {
            throw DuplicateEmailException("이미 등록된 이메일입니다: ${registerRequest.email}")
        }
    }

    override fun isEmailDuplicated(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isNicknameDuplicated(nickname: String): Boolean {
        TODO("Not yet implemented")
    }
}
