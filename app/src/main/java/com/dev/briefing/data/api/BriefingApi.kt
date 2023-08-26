package com.dev.briefing.data.api

import com.dev.briefing.data.model.BriefingDetailResponse
import com.dev.briefing.data.model.BriefingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BriefingApi {
    @GET("/briefings")
    fun getBriefingKeyword(
        @Query("briefings")brefingsDate:String,
        @Query("type")type:String,
    ): Call<BriefingResponse>

    @GET("/briefings/{id}")
    fun getBriefingDetail(
        @Path("id")id:Int,
    ): Call<BriefingDetailResponse>
}