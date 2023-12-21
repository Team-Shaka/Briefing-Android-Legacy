package com.dev.briefing.data.datasource

import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.GoogleRequest
import com.dev.briefing.data.model.GoogleSocialResponse
import com.dev.briefing.data.model.SingoutResponse
import com.dev.briefing.data.model.TokenRequest

interface AuthDataSource {
    suspend fun getLoginCode(identityToken: GoogleRequest): CommonResponse<GoogleSocialResponse>
    suspend fun withdrawal(memberId: Int): CommonResponse<SingoutResponse>
    suspend fun getAccessToken(refreshToken: TokenRequest): CommonResponse<GoogleSocialResponse>

}