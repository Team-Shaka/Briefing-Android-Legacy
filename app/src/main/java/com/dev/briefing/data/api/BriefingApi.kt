package com.dev.briefing.data.api

import com.dev.briefing.data.model.response.BriefingArticleResponse
import com.dev.briefing.data.model.response.BriefingCategoryArticlesResponse
import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.response.BriefingCompactArticleResponse
import com.dev.briefing.model.BriefingCategoryArticles
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BriefingApi {
    @GET("briefings")
    suspend fun getBriefingCategoryArticles(
        @Query("type") type: String,
        @Query("date") date: String?,
        @Query("timeOfDay") timeOfDay: String?
    ): CommonResponse<BriefingCategoryArticlesResponse>

    @GET("briefings/{id}")
    suspend fun getBriefingArticle(@Path("id") id: Long): CommonResponse<BriefingArticleResponse>
}