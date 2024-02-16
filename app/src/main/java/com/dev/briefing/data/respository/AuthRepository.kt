package com.dev.briefing.data.respository

import com.dev.briefing.data.model.MemberDeleteResponse
import com.dev.briefing.data.model.SocialLoginRequest
import com.dev.briefing.data.model.SocialLoginResponse
import com.dev.briefing.data.model.response.common.CommonResponse

interface AuthRepository {
    suspend fun signInWithSocialProvider(
        provider: String,
        idToken: SocialLoginRequest
    ): CommonResponse<SocialLoginResponse>

    suspend fun deleteMember(memberId: Int): CommonResponse<MemberDeleteResponse>

}