package com.dev.briefing.data.api

import com.dev.briefing.data.model.BriefingDetailResponse
import com.dev.briefing.data.model.BriefingPreview
import com.dev.briefing.data.model.BriefingResponse
import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.SetScrapRequest
import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.UnScrapResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BriefingApi {
    @GET("/briefings")
    suspend fun getBriefingKeyword(
        @Query("date")brefingsDate:String,
        @Query("type")type:String,
    ):CommonResponse<BriefingResponse>

    @GET("/briefings/{id}")
    suspend fun getBriefingDetail(
        @Path("id")id:Int,
    ): CommonResponse<BriefingDetailResponse>

    /**
     * 스크랩 등록하기
     * @Body memberId
     * @Body briefingId
     */
    @POST("/scraps/briefings")
    suspend fun setScrap(
        @Body memberInfo: SetScrapRequest,
    ): CommonResponse<SetScrapResponse>

    /**
     * 스크랩 해제하기
     * @@Path scrapId
     */
    @DELETE("/scraps/briefings/{briefingId}/members/{memberId}")
    suspend fun setUnScrap(
        @Path("briefingId") briefingId: Int,
        @Path("memberId") memberId: Int,
    ): CommonResponse<UnScrapResponse>

}