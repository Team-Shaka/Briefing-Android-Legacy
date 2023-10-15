package com.dev.briefing.data.respository

import com.dev.briefing.data.datasource.AuthDataSource
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.GoogleRequest
import com.dev.briefing.data.model.GoogleSocialResponse
import com.dev.briefing.data.model.SingoutResponse

class AuthRepositoryImpl(private val authDataSource: AuthDataSource): AuthRepository {
    override suspend fun getLoginToken(idToken: GoogleRequest): CommonResponse<GoogleSocialResponse> {
        return authDataSource.getLoginCode(idToken)
    }

    override suspend fun signOut(memberId: Int): CommonResponse<SingoutResponse> {
        return authDataSource.signOut(memberId)
    }
}