package com.dev.briefing.data.datasource

import com.dev.briefing.data.api.BriefingApi
import com.dev.briefing.data.model.response.BriefingArticleResponse
import com.dev.briefing.data.model.response.BriefingCategoryArticlesResponse
import com.dev.briefing.data.model.response.BriefingCompactArticleResponse
import com.dev.briefing.model.BriefingCategoryArticles
import com.dev.briefing.model.enum.BriefingArticleCategory
import com.dev.briefing.model.enum.TimeOfDay
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BriefingDataSourceImpl(private val briefingApi: BriefingApi) : BriefingDataSource {

    override suspend fun getBriefingCompactArticles(
        type: BriefingArticleCategory,
        date: LocalDate?,
        timeOfDay: TimeOfDay?
    ): BriefingCategoryArticlesResponse {
        return briefingApi.getBriefingCategoryArticles(
            type = type.typeName,
            date = date?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            timeOfDay = timeOfDay?.value
        ).result
    }

    override suspend fun getBriefingArticle(id: Long): BriefingArticleResponse {
        return briefingApi.getBriefingArticle(id).result
    }

}