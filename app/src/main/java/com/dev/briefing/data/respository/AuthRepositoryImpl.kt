package com.dev.briefing.data.respository

import com.dev.briefing.data.datasource.AuthDataSource
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.GoogleRequest
import com.dev.briefing.data.model.GoogleSocialResponse

class AuthRepositoryImpl(private val authDataSource: AuthDataSource): AuthRepository {
    override suspend fun getLoginToken(idToken: GoogleRequest): CommonResponse<GoogleSocialResponse> {
        return authDataSource.getLoginCode(idToken)
    }
}