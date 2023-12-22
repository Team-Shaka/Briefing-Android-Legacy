package com.dev.briefing.data.respository

import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.SocialLoginResponse
import com.dev.briefing.data.model.ScrapResponse
import com.dev.briefing.data.model.TokenRequest

interface ScrapRepository {
    suspend fun getScrap(memberId: Int): CommonResponse<List<ScrapResponse>>
    suspend fun getAccessToken(refreshToken: TokenRequest): CommonResponse<SocialLoginResponse>

}