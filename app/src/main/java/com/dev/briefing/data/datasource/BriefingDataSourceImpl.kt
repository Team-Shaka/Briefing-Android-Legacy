package com.dev.briefing.data.datasource

import com.dev.briefing.data.api.BriefingApi
import com.dev.briefing.data.model.BriefingDetailResponse
import com.dev.briefing.data.model.BriefingResponse

class BriefingDataSourceImpl(private val briefingApi: BriefingApi):BriefingDataSource {

    override suspend fun getBriefingKeyword(briefingDate:String,type:String): BriefingResponse {
        return briefingApi.getBriefingKeyword(briefingDate,type)
    }
    override suspend fun getBriefingDetail(id:Int): BriefingDetailResponse{
        return briefingApi.getBriefingDetail(id)
    }
}