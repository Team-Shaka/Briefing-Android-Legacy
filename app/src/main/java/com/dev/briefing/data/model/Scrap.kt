package com.dev.briefing.data.model

import com.google.gson.annotations.SerializedName

data class SetScrapResponse(
    @SerializedName("scrapId")
    val scrapId: Int,
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("briefingId")
    val briefingId: Int,
    @SerializedName("createdAt")
    val createdAt: String,
)

data class UnScrapResponse(
    @SerializedName("scrapId")
    val scrapId: Int,
    @SerializedName("deletedAt")
    val deletedAt: String,
)

data class SetScrapRequest(
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("briefingId")
    val briefingId: Int,
)

data class ScrapResponse(
    @SerializedName("briefingId")
    val briefingId: Int,
    @SerializedName("ranks")
    val ranks: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("subtitle")
    val subtitle: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("gptModel")
    val gptModel: String,
    @SerializedName("timeOfDay")
    val timeOfDay: String,
)
