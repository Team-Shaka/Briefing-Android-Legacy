package com.dev.briefing.data.respository

import com.dev.briefing.data.datasource.AuthDataSource
import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.SocialLoginRequest
import com.dev.briefing.data.model.SocialLoginResponse
import com.dev.briefing.data.model.SingoutResponse
import com.dev.briefing.data.model.TokenRequest

class AuthRepositoryImpl(private val authDataSource: AuthDataSource) : AuthRepository {
    override suspend fun signInWithSocialProvider(
        provider: String,
        idToken: SocialLoginRequest
    ): CommonResponse<SocialLoginResponse> {
        return authDataSource.signInWithSocialProvider(provider, idToken)
    }

    override suspend fun signOut(memberId: Int): CommonResponse<SingoutResponse> {
        return authDataSource.withdrawal(memberId)
    }

    override suspend fun getAccessToken(refreshToken: TokenRequest): CommonResponse<SocialLoginResponse> {
        return authDataSource.getAccessToken(refreshToken)
    }
}