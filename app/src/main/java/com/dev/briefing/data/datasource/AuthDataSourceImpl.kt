package com.dev.briefing.data.datasource

import com.dev.briefing.data.api.AuthApi
import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.GoogleRequest
import com.dev.briefing.data.model.GoogleSocialResponse
import com.dev.briefing.data.model.SingoutResponse
import com.dev.briefing.data.model.TokenRequest

class AuthDataSourceImpl(private val authApi: AuthApi) : AuthDataSource {
    override suspend fun getLoginCode(identityToken: GoogleRequest): CommonResponse<GoogleSocialResponse> {
        return authApi.getLoginToken(identityToken)
    }

    override suspend fun withdrawal(memberId: Int): CommonResponse<SingoutResponse> {
        return authApi.withdrawal(memberId)
    }

    override suspend fun getAccessToken(refreshToken: TokenRequest): CommonResponse<GoogleSocialResponse> {
        return authApi.getAccessToken(refreshToken = refreshToken)

    }
}