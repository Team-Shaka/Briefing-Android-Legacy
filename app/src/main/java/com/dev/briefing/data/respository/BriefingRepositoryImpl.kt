package com.dev.briefing.data.respository

import com.dev.briefing.data.datasource.BriefingDataSource
import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.GoogleSocialResponse
import com.dev.briefing.data.model.SetScrapRequest
import com.dev.briefing.data.model.SetScrapResponse
import com.dev.briefing.data.model.TokenRequest
import com.dev.briefing.data.model.UnScrapResponse
import com.dev.briefing.data.model.response.toDomain
import com.dev.briefing.model.BriefingArticle
import com.dev.briefing.model.BriefingCategoryArticles
import com.dev.briefing.model.BriefingCompactArticle
import com.dev.briefing.model.enum.BriefingArticleCategory
import com.dev.briefing.model.enum.TimeOfDay
import java.time.LocalDate

class BriefingRepositoryImpl(private val briefingDataSource: BriefingDataSource) :
    BriefingRepository {
    override suspend fun getBriefings(
        briefingArticleCategory: BriefingArticleCategory,
        dateLocalDate: LocalDate?,
        timeOfDay: TimeOfDay?
    ): BriefingCategoryArticles {
        return briefingDataSource.getBriefingCompactArticles(
            briefingArticleCategory,
            dateLocalDate,
            timeOfDay
        ).toDomain()
    }

    override suspend fun getBriefingDetail(id: Int): BriefingArticle {
        return briefingDataSource.getBriefingArticle(id).toDomain()
    }
}