package com.dev.briefing.data.datasource

import com.dev.briefing.data.api.ScrapApi
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.ScrapResponse
import com.dev.briefing.data.model.SetScrapRequest
import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.UnScrapResponse

class ScrapDataSourceImpl(private val scrapApi: ScrapApi):ScrapDataSource{
    override suspend fun setScrap(memberInfo: SetScrapRequest): CommonResponse<SetScrapResponse> {
        return scrapApi.setScrap(memberInfo)
    }

    override suspend fun setUnScrap(scrapId: Int): CommonResponse<UnScrapResponse> {
        return scrapApi.setUnScrap(scrapId)
    }

    override suspend fun getScrap(memberId: Int): CommonResponse<List<ScrapResponse>> {
        return scrapApi.getScrap(memberId)
    }

}