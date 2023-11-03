package com.dev.briefing.data.model

import com.google.gson.annotations.SerializedName

data class BriefingResponse(
    @SerializedName("createdAt")
    val created_at: String,
    @SerializedName("briefings")
    val briefings: List<BriefingPreview>
)
data class BriefingPreview(
    @SerializedName("id")
    val id: Int,
    @SerializedName("ranks")
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
    @SerializedName("ranks")
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
var tmpBriefingResponse = BriefingDetailResponse(
    id = 0,
    rank = 0,
    title = "데이터 로딩중",
    subtitle = "",
    content = "준비중..",
    date = "2023-08-27",
    isScrap = false,
    isBriefOpen = false,
    isWarning = false,
    articles = listOf(
        Article(id = 1, press = "로딩중", title = "로딩중", "로딩중")
    )
)