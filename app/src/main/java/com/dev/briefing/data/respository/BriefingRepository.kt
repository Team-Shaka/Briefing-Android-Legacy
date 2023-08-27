package com.dev.briefing.data.respository


import com.dev.briefing.data.model.BriefingDetailResponse
import com.dev.briefing.data.model.BriefingResponse


interface BriefingRepository {

    suspend fun getBriefingKeyword(briefingDate:String,type:String): BriefingResponse
    suspend fun getBriefingDetail(id:Int): BriefingDetailResponse
}