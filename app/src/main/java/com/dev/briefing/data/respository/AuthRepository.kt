package com.dev.briefing.data.respository

import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.SocialLoginRequest
import com.dev.briefing.data.model.SocialLoginResponse
import com.dev.briefing.data.model.SingoutResponse
import com.dev.briefing.data.model.TokenRequest

interface AuthRepository {
    suspend fun signInWithSocialProvider(
        provider: String,
        idToken: SocialLoginRequest
    ): CommonResponse<SocialLoginResponse>

    suspend fun signOut(memberId: Int): CommonResponse<SingoutResponse>
    suspend fun getAccessToken(refreshToken: TokenRequest): CommonResponse<SocialLoginResponse>


}