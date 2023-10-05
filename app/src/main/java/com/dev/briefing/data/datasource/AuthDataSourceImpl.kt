package com.dev.briefing.data.datasource

import com.dev.briefing.data.api.AuthApi
import com.dev.briefing.data.api.BriefingApi
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.GoogleRequest
import com.dev.briefing.data.model.GoogleSocialResponse

class AuthDataSourceImpl(private val authApi: AuthApi) : AuthDataSource {
    override suspend fun getLoginCode(identityToken: GoogleRequest): CommonResponse<GoogleSocialResponse> {
        return authApi.getLoginToken(identityToken)
    }

}