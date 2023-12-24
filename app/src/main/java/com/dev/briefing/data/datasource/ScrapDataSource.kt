package com.dev.briefing.data.datasource

import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.ScrapResponse
import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.UnScrapResponse

interface ScrapDataSource {

    suspend fun getScrap(memberId: Int): CommonResponse<List<ScrapResponse>>

    suspend fun setScrap(memberId: Int, articleId: Long): CommonResponse<SetScrapResponse>

    suspend fun unScrap(memberId: Int, articleId: Long): CommonResponse<UnScrapResponse>
}