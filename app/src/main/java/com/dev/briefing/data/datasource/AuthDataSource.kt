package com.dev.briefing.data.datasource

import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.SocialLoginRequest
import com.dev.briefing.data.model.SocialLoginResponse
import com.dev.briefing.data.model.SingoutResponse
import com.dev.briefing.data.model.TokenRequest

interface AuthDataSource {
    suspend fun signInWithSocialProvider(provider : String, identityToken: SocialLoginRequest): CommonResponse<SocialLoginResponse>
    suspend fun withdrawal(memberId: Int): CommonResponse<SingoutResponse>
    suspend fun getAccessToken(refreshToken: TokenRequest): CommonResponse<SocialLoginResponse>

}