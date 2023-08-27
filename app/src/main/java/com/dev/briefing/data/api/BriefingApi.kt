package com.dev.briefing.data.api

import com.dev.briefing.data.model.BriefingDetailResponse
import com.dev.briefing.data.model.BriefingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BriefingApi {
    @GET("/briefings")
    suspend fun getBriefingKeyword(
        @Query("date")brefingsDate:String,
        @Query("type")type:String,
    ): BriefingResponse

    @GET("/briefings/{id}")
    fun getBriefingDetail(
        @Path("id")id:Int,
    ): Call<BriefingDetailResponse>
}