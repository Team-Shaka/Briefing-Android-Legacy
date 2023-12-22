package com.dev.briefing.data.datasource

import com.dev.briefing.data.api.AuthApi
import com.dev.briefing.data.api.ScrapApi
import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.SocialLoginResponse
import com.dev.briefing.data.model.ScrapResponse
import com.dev.briefing.data.model.SetScrapRequest
import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.TokenRequest
import com.dev.briefing.data.model.UnScrapResponse

class ScrapDataSourceImpl(private val scrapApi: ScrapApi, private val authApi: AuthApi) :
    ScrapDataSource {

    override suspend fun getScrap(memberId: Int): CommonResponse<List<ScrapResponse>> {
        return scrapApi.getScrap(memberId)
    }

    override suspend fun setScrap(memberId: Int, articleId: Long): CommonResponse<SetScrapResponse> {
        return scrapApi.setScrap(SetScrapRequest(memberId, articleId))
    }

    override suspend fun unScrap(memberId: Int, articleId: Long): CommonResponse<UnScrapResponse> {
        return scrapApi.setUnScrap(articleId, memberId)
    }
}