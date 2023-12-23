package com.dev.briefing.model.enum

enum class BriefingArticleCategory(val typeName: String) {
    KOREA("KOREA"),
    GLOBAL("GLOBAL"),
    SOCIAL("SOCIAL"),
    SCIENCE("SCIENCE"),
    ECONOMY("ECONOMY");

    companion object {
        fun fromTypeName(typeName: String): BriefingArticleCategory {
            return values().firstOrNull { it.typeName.equals(typeName, ignoreCase = true) }
                ?: throw IllegalArgumentException("Invalid typeName for ArticleType: $typeName")
        }
    }
}