package com.dev.briefing.model

import com.google.gson.annotations.SerializedName

data class BriefingCompactArticle(
    val id: Int,
    val ranks: Int,
    val title: String,
    val subtitle: String,
    val scrapCount: Int
)