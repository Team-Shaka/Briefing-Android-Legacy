package com.dev.briefing.data.respository


import com.dev.briefing.data.model.BriefingDetailResponse
import com.dev.briefing.data.model.BriefingPreview
import com.dev.briefing.data.model.BriefingResponse
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.SetScrapRequest
import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.UnScrapResponse


interface BriefingRepository {

    suspend fun getBriefingKeyword(briefingDate:String,type:String): CommonResponse<BriefingResponse>
    suspend fun getBriefingDetail(id:Int): CommonResponse<BriefingDetailResponse>

    suspend fun setScrap(memberInfo: SetScrapRequest): CommonResponse<SetScrapResponse>
    suspend fun unScrap(memberId:Int,briefingId:Int): CommonResponse<UnScrapResponse>
}