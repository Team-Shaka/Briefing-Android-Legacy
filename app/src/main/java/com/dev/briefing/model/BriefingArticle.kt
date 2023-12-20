package com.dev.briefing.model

import com.dev.briefing.data.model.response.RelatedArticleResponse
import com.dev.briefing.model.enum.BriefingArticleCategory
import com.dev.briefing.model.enum.TimeOfDay
import java.time.LocalDate

data class BriefingArticle(
    val id: Int,
    val ranks: Int,
    val title: String,
    val subtitle: String,
    val content: String,
    val createdDate: LocalDate,
    val relatedArticles: List<RelatedArticle>,
    val isScrap: Boolean,
    val isBriefingOpen: Boolean,
    val isWarning: Boolean,
    val scrapCount: Int,
    val gptModel: String,
    val timeOfDay: TimeOfDay,
    val category: BriefingArticleCategory
)