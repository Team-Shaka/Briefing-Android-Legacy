package com.dev.briefing.presentation.home

import com.dev.briefing.R
import com.dev.briefing.model.enum.BriefingArticleCategory

enum class HomeCategory(val tabTitle: Int, val category: BriefingArticleCategory) {
    SOCIETY(R.string.title_tab_home_society, BriefingArticleCategory.SOCIAL),
    SCIENCE(R.string.title_tab_home_science, BriefingArticleCategory.SCIENCE),
    GLOBAL(R.string.title_tab_home_global, BriefingArticleCategory.GLOBAL),
    ECONOMY(R.string.title_tab_home_economy, BriefingArticleCategory.ECONOMY)
}