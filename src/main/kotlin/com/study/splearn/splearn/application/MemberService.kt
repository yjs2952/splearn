package com.study.splearn.splearn.application

import com.study.splearn.splearn.application.provided.MemberRegister
import com.study.splearn.splearn.application.required.EmailSender
import com.study.splearn.splearn.application.required.MemberRepository
import com.study.splearn.splearn.domain.Member
import com.study.splearn.splearn.domain.MemberRegisterRequest
import com.study.splearn.splearn.domain.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val emailSender: EmailSender,
    private val passwordEncoder: PasswordEncoder,
) : MemberRegister {

    override fun register(registerRequest: MemberRegisterRequest): Member {
        // check
        val member = Member.register(registerRequest, passwordEncoder)
        memberRepository.save(member)

        emailSender.send(member.email, "등록을 완료해주세요.", "아래 링크를 클릭해서 등록을 완료해주세요.")
        return member
    }

    override fun isEmailDuplicated(email: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isNicknameDuplicated(nickname: String): Boolean {
        TODO("Not yet implemented")
    }
}
