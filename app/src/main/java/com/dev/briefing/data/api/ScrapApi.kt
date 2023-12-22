package com.dev.briefing.data.api

import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.ScrapResponse
import com.dev.briefing.data.model.SetScrapRequest
import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.UnScrapResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ScrapApi {
    /**
     * 스크랩 리스트 반환하기
     * @Path memberId
     */
    @GET("scraps/briefings/members/{memberId}")
    suspend fun getScrap(
        @Path("memberId") memberId: Int,
    ): CommonResponse<List<ScrapResponse>>

    /**
     * 스크랩 등록하기
     * @Body memberId
     * @Body briefingId
     */
    @POST("scraps/briefings")
    suspend fun setScrap(
        @Body memberInfo: SetScrapRequest,
    ): CommonResponse<SetScrapResponse>

    /**
     * 스크랩 해제하기
     * @@Path scrapId
     */
    @DELETE("scraps/briefings/{briefingId}/members/{memberId}")
    suspend fun setUnScrap(
        @Path("briefingId") briefingId: Long,
        @Path("memberId") memberId: Int,
    ): CommonResponse<UnScrapResponse>
}