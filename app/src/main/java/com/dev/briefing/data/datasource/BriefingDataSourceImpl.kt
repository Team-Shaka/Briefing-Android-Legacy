package com.dev.briefing.data.datasource

import com.dev.briefing.data.api.BriefingApi
import com.dev.briefing.data.model.BriefingDetailResponse
import com.dev.briefing.data.model.BriefingResponse
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.SetScrapRequest
import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.UnScrapResponse

class BriefingDataSourceImpl(private val briefingApi: BriefingApi):BriefingDataSource {

    override suspend fun getBriefingKeyword(briefingDate:String,type:String): BriefingResponse {
        return briefingApi.getBriefingKeyword(briefingDate,type)
    }
    override suspend fun getBriefingDetail(id:Int): BriefingDetailResponse{
        return briefingApi.getBriefingDetail(id)
    }
    override suspend fun setScrap(memberInfo: SetScrapRequest): CommonResponse<SetScrapResponse> {
        return briefingApi.setScrap(memberInfo)
    }

    override suspend fun setUnScrap(memberId: Int,briefingId:Int): CommonResponse<UnScrapResponse> {
        return briefingApi.setUnScrap(memberId,briefingId)
    }

}