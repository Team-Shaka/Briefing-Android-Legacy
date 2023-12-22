package com.dev.briefing.data.datasource

import com.dev.briefing.data.api.AuthApi
import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.SocialLoginRequest
import com.dev.briefing.data.model.SocialLoginResponse
import com.dev.briefing.data.model.SingoutResponse
import com.dev.briefing.data.model.TokenRequest

class AuthDataSourceImpl(private val authApi: AuthApi) : AuthDataSource {
    override suspend fun signInWithSocialProvider(provider : String, identityToken: SocialLoginRequest): CommonResponse<SocialLoginResponse> {
        return authApi.signInWithSocialProvider(provider, identityToken)
    }

    override suspend fun withdrawal(memberId: Int): CommonResponse<SingoutResponse> {
        return authApi.withdrawal(memberId)
    }

    override suspend fun getAccessToken(refreshToken: TokenRequest): CommonResponse<SocialLoginResponse> {
        return authApi.getAccessToken(refreshToken = refreshToken)

    }
}