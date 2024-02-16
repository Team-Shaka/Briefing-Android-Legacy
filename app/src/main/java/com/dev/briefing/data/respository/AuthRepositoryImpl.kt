package com.dev.briefing.data.respository

import com.dev.briefing.data.datasource.AuthDataSource
import com.dev.briefing.data.model.MemberDeleteResponse
import com.dev.briefing.data.model.SocialLoginRequest
import com.dev.briefing.data.model.SocialLoginResponse
import com.dev.briefing.data.model.response.common.CommonResponse

class AuthRepositoryImpl(private val authDataSource: AuthDataSource) : AuthRepository {
    override suspend fun signInWithSocialProvider(
        provider: String,
        idToken: SocialLoginRequest
    ): CommonResponse<SocialLoginResponse> {
        return authDataSource.signInWithSocialProvider(provider, idToken)
    }

    override suspend fun deleteMember(memberId: Int): CommonResponse<MemberDeleteResponse> {
        return authDataSource.deleteMember(memberId)
    }

}