package com.dev.briefing.data.datasource

import com.dev.briefing.data.api.AuthApi
import com.dev.briefing.data.api.ScrapApi
import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.GoogleSocialResponse
import com.dev.briefing.data.model.ScrapResponse
import com.dev.briefing.data.model.TokenRequest

class ScrapDataSourceImpl(private val scrapApi: ScrapApi, private val authApi: AuthApi) :
    ScrapDataSource {

    override suspend fun getScrap(memberId: Int): CommonResponse<List<ScrapResponse>> {
        return scrapApi.getScrap(memberId)
    }

    override suspend fun getAccessToken(refreshToken: TokenRequest): CommonResponse<GoogleSocialResponse> {
        return authApi.getAccessToken(refreshToken)
    }

}