package com.dev.briefing.data.datasource

import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.GoogleSocialResponse
import com.dev.briefing.data.model.ScrapResponse
import com.dev.briefing.data.model.TokenRequest

interface ScrapDataSource {

    suspend fun getScrap(memberId: Int): CommonResponse<List<ScrapResponse>>
    suspend fun getAccessToken(refreshToken: TokenRequest): CommonResponse<GoogleSocialResponse>

}