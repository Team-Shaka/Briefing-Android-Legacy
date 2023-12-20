package com.dev.briefing.data.respository

import com.dev.briefing.data.datasource.ScrapDataSource
import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.GoogleSocialResponse
import com.dev.briefing.data.model.ScrapResponse
import com.dev.briefing.data.model.TokenRequest

class ScrapRepositoryImpl(private val scrapDataSource: ScrapDataSource): ScrapRepository {
    override suspend fun getScrap(memberId: Int): CommonResponse<List<ScrapResponse>> {
        return scrapDataSource.getScrap(memberId)
    }

    override suspend fun getAccessToken(refreshToken: TokenRequest): CommonResponse<GoogleSocialResponse> {
        return scrapDataSource.getAccessToken(refreshToken)
    }
}