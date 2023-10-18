package com.dev.briefing.data.respository

import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.GoogleSocialResponse
import com.dev.briefing.data.model.ScrapResponse
import com.dev.briefing.data.model.SetScrapRequest
import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.TokenRequest
import com.dev.briefing.data.model.UnScrapResponse

interface ScrapRepository {

    suspend fun getScrap(memberId: Int): CommonResponse<List<ScrapResponse>>
    suspend fun getAccessToken(refreshToken: TokenRequest): CommonResponse<GoogleSocialResponse>

}