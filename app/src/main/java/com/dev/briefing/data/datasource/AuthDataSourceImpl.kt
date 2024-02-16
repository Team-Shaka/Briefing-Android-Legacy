package com.dev.briefing.data.datasource

import com.dev.briefing.data.api.AuthApi
import com.dev.briefing.data.model.MemberDeleteResponse
import com.dev.briefing.data.model.SocialLoginRequest
import com.dev.briefing.data.model.SocialLoginResponse
import com.dev.briefing.data.model.response.common.CommonResponse

class AuthDataSourceImpl(private val authApi: AuthApi) : AuthDataSource {
    override suspend fun signInWithSocialProvider(provider : String, identityToken: SocialLoginRequest): CommonResponse<SocialLoginResponse> {
        return authApi.signInWithSocialProvider(provider, identityToken)
    }

    override suspend fun deleteMember(memberId: Int): CommonResponse<MemberDeleteResponse> {
        return authApi.deleteMember(memberId)
    }

}