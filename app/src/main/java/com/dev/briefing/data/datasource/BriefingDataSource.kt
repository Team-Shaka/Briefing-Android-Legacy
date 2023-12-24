package com.dev.briefing.data.datasource

import com.dev.briefing.data.model.response.BriefingArticleResponse
import com.dev.briefing.data.model.response.BriefingCategoryArticlesResponse
import com.dev.briefing.model.enum.BriefingArticleCategory
import com.dev.briefing.model.enum.TimeOfDay
import java.time.LocalDate


interface BriefingDataSource {
    suspend fun getBriefingCompactArticles(
        type: BriefingArticleCategory,
        date: LocalDate? = null,
        timeOfDay: TimeOfDay? = null
    ): BriefingCategoryArticlesResponse

    suspend fun getBriefingArticle(id: Long): BriefingArticleResponse
}