package com.study.splearn.splearn.domain.member

data class MemberInfoUpdateRequest(
    val nickname: String,
    val profileAddress: String,
    val introduction: String,
) {

}
