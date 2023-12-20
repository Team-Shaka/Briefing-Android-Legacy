package com.dev.briefing.data.respository


import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.GoogleSocialResponse
import com.dev.briefing.data.model.SetScrapRequest
import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.TokenRequest
import com.dev.briefing.data.model.UnScrapResponse
import com.dev.briefing.model.BriefingArticle
import com.dev.briefing.model.BriefingCategoryArticles
import com.dev.briefing.model.BriefingCompactArticle
import com.dev.briefing.model.enum.BriefingArticleCategory
import com.dev.briefing.model.enum.TimeOfDay
import java.time.LocalDate


interface BriefingRepository {
    suspend fun getBriefings(
        briefingArticleCategory: BriefingArticleCategory,
        dateLocalDate: LocalDate? = null,
        timeOfDay: TimeOfDay? = null
    ): BriefingCategoryArticles

    suspend fun getBriefingDetail(id: Int): BriefingArticle
}