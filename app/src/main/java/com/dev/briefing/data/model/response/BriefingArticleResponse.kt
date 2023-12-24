package com.dev.briefing.data.model.response

import com.dev.briefing.model.BriefingArticle
import com.dev.briefing.model.enum.BriefingArticleCategory
import com.dev.briefing.model.enum.TimeOfDay
import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class BriefingArticleResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("ranks") val ranks: Int,
    @SerializedName("title") val title: String,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("content") val content: String,
    @SerializedName("date") val date: String,
    @SerializedName("articles") val articles: List<RelatedArticleResponse>,
    @SerializedName("isScrap") val isScrap: Boolean,
    @SerializedName("isBriefingOpen") val isBriefingOpen: Boolean,
    @SerializedName("isWarning") val isWarning: Boolean,
    @SerializedName("scrapCount") val scrapCount: Int,
    @SerializedName("gptModel") val gptModel: String,
    @SerializedName("timeOfDay") val timeOfDay: String,
    @SerializedName("type") val type: String
)

fun BriefingArticleResponse.toDomain(): BriefingArticle {
    return BriefingArticle(
        id = this.id,
        ranks = this.ranks,
        title = this.title,
        subtitle = this.subtitle,
        content = this.content,
        createdDate = LocalDate.parse(this.date, DateTimeFormatter.ISO_DATE),
        relatedArticles = this.articles.map { it.toDomain() },
        isScrap = this.isScrap,
        isBriefingOpen = this.isBriefingOpen,
        isWarning = this.isWarning,
        scrapCount = this.scrapCount,
        gptModel = this.gptModel,
        timeOfDay = TimeOfDay.fromValue(this.timeOfDay),
        category = BriefingArticleCategory.fromTypeName(this.type)
    )
}
