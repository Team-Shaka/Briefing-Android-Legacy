package com.dev.briefing.data.datasource

import androidx.work.WorkInfo
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.ScrapResponse
import com.dev.briefing.data.model.SetScrapRequest
import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.UnScrapResponse

interface ScrapDataSource {
    suspend fun setScrap(memberInfo: SetScrapRequest): CommonResponse<SetScrapResponse>
    suspend fun setUnScrap(scrapId: Int): CommonResponse<UnScrapResponse>
    suspend fun getScrap(memberId: Int): CommonResponse<List<ScrapResponse>>
}