package com.dev.briefing.data.model

import com.google.gson.annotations.SerializedName

data class BriefingResponse(
    @SerializedName("created_at")
    val created_at: String,
    @SerializedName("briefings")
    val briefings: List<BriefingPreview>?
)
data class BriefingPreview(
    @SerializedName("id")
    val id: Int,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("subtitle")
    val subtitle: String,
)
data class BriefingDetailResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("isScrap")
    val isScrap: Boolean,
    @SerializedName("isBriefOpen")
    val isBriefOpen: Boolean,
    @SerializedName("isWarning")
    val isWarning: Boolean,
    @SerializedName("articles")
    val articles: List<Article>,
)
data class Article(
    @SerializedName("id")
    val id: Int,
    @SerializedName("press")
    val press: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
)
