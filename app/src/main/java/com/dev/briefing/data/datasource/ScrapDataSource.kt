package com.dev.briefing.data.datasource

import androidx.work.WorkInfo
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.GoogleSocialResponse
import com.dev.briefing.data.model.ScrapResponse
import com.dev.briefing.data.model.SetScrapRequest
import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.TokenRequest
import com.dev.briefing.data.model.UnScrapResponse

interface ScrapDataSource {

    suspend fun getScrap(memberId: Int): CommonResponse<List<ScrapResponse>>
    suspend fun getAccessToken(refreshToken: TokenRequest): CommonResponse<GoogleSocialResponse>

}