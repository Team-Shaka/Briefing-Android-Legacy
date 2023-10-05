package com.dev.briefing.data.respository

import com.dev.briefing.data.datasource.ScrapDataSource
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.ScrapResponse
import com.dev.briefing.data.model.SetScrapRequest
import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.UnScrapResponse

class ScrapRepositoryImpl(private val scrapDataSource: ScrapDataSource): ScrapRepository {
    override suspend fun setScrap(memberInfo: SetScrapRequest): CommonResponse<SetScrapResponse> {
        return scrapDataSource.setScrap(memberInfo)
    }

    override suspend fun unScrap(scrapId: Int): CommonResponse<UnScrapResponse> {
        return scrapDataSource.setUnScrap(scrapId)
    }

    override suspend fun getScrap(memberId: Int): CommonResponse<List<ScrapResponse>> {
        return scrapDataSource.getScrap(memberId)
    }
}