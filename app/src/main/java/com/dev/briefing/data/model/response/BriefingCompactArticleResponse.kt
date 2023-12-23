package com.dev.briefing.data.model.response

import com.dev.briefing.model.BriefingCompactArticle
import com.google.gson.annotations.SerializedName

data class BriefingCompactArticleResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("ranks") val ranks: Int,
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("scrapCount") val scrapCount: Int
)

fun BriefingCompactArticleResponse.toDomain(): BriefingCompactArticle {
    return BriefingCompactArticle(
        id = this.id,
        ranks = this.ranks,
        title = this.title,
        subtitle = this.subtitle,
        scrapCount = this.scrapCount
    )
}