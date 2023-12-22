package com.dev.briefing.data.respository

import com.dev.briefing.data.datasource.ScrapDataSource
import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.SocialLoginResponse
import com.dev.briefing.data.model.ScrapResponse
import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.TokenRequest
import com.dev.briefing.data.model.UnScrapResponse

class ScrapRepositoryImpl(private val scrapDataSource: ScrapDataSource): ScrapRepository {
    override suspend fun getScrap(memberId: Int): CommonResponse<List<ScrapResponse>> {
        return scrapDataSource.getScrap(memberId)
    }

    override suspend fun setScrap(memberId: Int, articleId: Long): CommonResponse<SetScrapResponse> {
        return scrapDataSource.setScrap(memberId, articleId)
    }

    override suspend fun unScrap(memberId: Int, articleId: Long): CommonResponse<UnScrapResponse> {
        return scrapDataSource.unScrap(memberId, articleId)
    }
}