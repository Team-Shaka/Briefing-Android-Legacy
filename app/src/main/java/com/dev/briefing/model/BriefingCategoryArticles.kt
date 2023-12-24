package com.dev.briefing.model

import java.util.Date

data class BriefingCategoryArticles(
    val createdAt: Date,
    val briefingCompactArticles: List<BriefingCompactArticle>
) {
}