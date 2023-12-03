package com.dev.briefing.presentation.home

import com.dev.briefing.R

enum class HomeCategory(val tabTitle: Int, val tabCategoryId: String) {
    SOCIETY(R.string.title_tab_home_society, "society"),
    SCIENCE(R.string.title_tab_home_science, "science"),
    GLOBAL(R.string.title_tab_home_global, "global"),
    ECONOMY(R.string.title_tab_home_economy, "economy")
}