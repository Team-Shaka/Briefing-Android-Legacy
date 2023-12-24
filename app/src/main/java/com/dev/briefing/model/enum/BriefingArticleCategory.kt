package com.dev.briefing.model.enum

import com.dev.briefing.R

enum class BriefingArticleCategory(val typeId: String, val typeName: Int) {
    KOREA("KOREA", R.string.name_category_korea),
    GLOBAL("GLOBAL", R.string.name_category_global),
    SOCIAL("SOCIAL", R.string.name_category_society),
    SCIENCE("SCIENCE", R.string.name_category_science),
    ECONOMY("ECONOMY", R.string.name_category_economy);

    companion object {
        fun fromTypeName(typeName: String): BriefingArticleCategory {
            return values().firstOrNull { it.typeId.equals(typeName, ignoreCase = true) }
                ?: throw IllegalArgumentException("Invalid typeName for ArticleType: $typeName")
        }
    }
}