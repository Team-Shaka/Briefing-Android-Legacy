package com.dev.briefing.presentation.home

import com.dev.briefing.model.BriefingArticle
import com.dev.briefing.model.BriefingCategoryArticles
import com.dev.briefing.model.BriefingCompactArticle
import com.dev.briefing.model.enum.BriefingArticleCategory

data class HomeBriefingArticleUiState(
    val currentLoadingCategories: Set<BriefingArticleCategory> = setOf(),
    val errorOccurCategories: Map<BriefingArticleCategory, String> = mapOf(),
    val briefingArticles: Map<BriefingArticleCategory, BriefingCategoryArticles> = mapOf(),
) {
}