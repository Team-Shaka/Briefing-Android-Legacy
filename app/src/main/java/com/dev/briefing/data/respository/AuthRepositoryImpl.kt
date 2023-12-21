package com.dev.briefing.data.respository

import com.dev.briefing.data.datasource.AuthDataSource
import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.GoogleRequest
import com.dev.briefing.data.model.GoogleSocialResponse
import com.dev.briefing.data.model.SingoutResponse
import com.dev.briefing.data.model.TokenRequest

class AuthRepositoryImpl(private val authDataSource: AuthDataSource): AuthRepository {
    override suspend fun getLoginToken(idToken: GoogleRequest): CommonResponse<GoogleSocialResponse> {
        return authDataSource.getLoginCode(idToken)
    }

    override suspend fun signOut(memberId: Int): CommonResponse<SingoutResponse> {
        return authDataSource.withdrawal(memberId)
    }

    override suspend fun getAccessToken(refreshToken: TokenRequest): CommonResponse<GoogleSocialResponse> {
        return authDataSource.getAccessToken(refreshToken)
    }
}