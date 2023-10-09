package com.dev.briefing.data.respository


import com.dev.briefing.data.model.BriefingDetailResponse
import com.dev.briefing.data.model.BriefingResponse
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.SetScrapRequest
import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.UnScrapResponse


interface BriefingRepository {

    suspend fun getBriefingKeyword(briefingDate:String,type:String): BriefingResponse
    suspend fun getBriefingDetail(id:Int): BriefingDetailResponse

    suspend fun setScrap(memberInfo: SetScrapRequest): CommonResponse<SetScrapResponse>
    suspend fun unScrap(scrapId:Int): CommonResponse<UnScrapResponse>
}