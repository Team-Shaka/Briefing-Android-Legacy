package com.dev.briefing.data.datasource

import com.dev.briefing.data.api.AuthApi
import com.dev.briefing.data.api.BriefingApi
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.GoogleRequest
import com.dev.briefing.data.model.GoogleSocialResponse
import com.dev.briefing.data.model.SingoutResponse

class AuthDataSourceImpl(private val authApi: AuthApi) : AuthDataSource {
    override suspend fun getLoginCode(identityToken: GoogleRequest): CommonResponse<GoogleSocialResponse> {
        return authApi.getLoginToken(identityToken)
    }

    override suspend fun signOut(memberId: Int): CommonResponse<SingoutResponse> {
        return authApi.signOut(memberId)
    }
}