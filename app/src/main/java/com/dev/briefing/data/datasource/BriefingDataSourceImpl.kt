package com.dev.briefing.data.datasource

import com.dev.briefing.data.api.BriefingApi
import com.dev.briefing.data.model.BriefingDetailResponse
import com.dev.briefing.data.model.BriefingPreview
import com.dev.briefing.data.model.BriefingResponse
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.SetScrapRequest
import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.UnScrapResponse

class BriefingDataSourceImpl(private val briefingApi: BriefingApi):BriefingDataSource {

    override suspend fun getBriefingKeyword(briefingDate:String,type:String): CommonResponse<BriefingResponse> {
        return briefingApi.getBriefingKeyword(briefingDate,type)
    }
    override suspend fun getBriefingDetail(id:Int): CommonResponse<BriefingDetailResponse>{
        return briefingApi.getBriefingDetail(id)
    }
    override suspend fun setScrap(memberInfo: SetScrapRequest): CommonResponse<SetScrapResponse> {
        return briefingApi.setScrap(memberInfo)
    }

    override suspend fun setUnScrap(memberId: Int,briefingId:Int): CommonResponse<UnScrapResponse> {
        return briefingApi.setUnScrap(briefingId = briefingId,memberId = memberId)
    }

}