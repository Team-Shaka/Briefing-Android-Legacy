package com.dev.briefing.data.respository

import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.UnScrapResponse
import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.model.Scrap

interface ScrapRepository {

    suspend fun getScrap(memberId: Int): List<Scrap>

    suspend fun setScrap(memberId: Int, articleId: Long): CommonResponse<SetScrapResponse>

    suspend fun unScrap(memberId: Int, articleId: Long): CommonResponse<UnScrapResponse>
}