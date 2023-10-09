package com.dev.briefing.data.respository

import com.dev.briefing.data.api.BriefingApi
import com.dev.briefing.data.datasource.BriefingDataSource
import com.dev.briefing.data.model.BriefingDetailResponse
import com.dev.briefing.data.model.BriefingPreview
import com.dev.briefing.data.model.BriefingResponse
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.SetScrapRequest
import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.UnScrapResponse

class BriefingRepositoryImpl(private val briefDataSource: BriefingDataSource) : BriefingRepository {
    override suspend fun getBriefingKeyword(briefingDate: String, type: String): CommonResponse<BriefingResponse> {
        return briefDataSource.getBriefingKeyword(briefingDate, type)
    }

    override suspend fun getBriefingDetail(id: Int): CommonResponse<BriefingDetailResponse> {
        return briefDataSource.getBriefingDetail(id)
    }

    override suspend fun setScrap(memberInfo: SetScrapRequest): CommonResponse<SetScrapResponse> {
        return briefDataSource.setScrap(memberInfo)
    }

    override suspend fun unScrap(memberId: Int, briefingId: Int): CommonResponse<UnScrapResponse> {
        return briefDataSource.setUnScrap(memberId, briefingId)
    }
}