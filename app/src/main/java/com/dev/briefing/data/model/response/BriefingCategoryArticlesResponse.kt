package com.dev.briefing.data.model.response

import com.dev.briefing.model.BriefingCategoryArticles
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class BriefingCategoryArticlesResponse(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("briefings")
    val briefings: List<BriefingCompactArticleResponse>
)

fun BriefingCategoryArticlesResponse.toDomain(): BriefingCategoryArticles {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
    val parsedDate = dateFormat.parse(createdAt) ?: Date()

    return BriefingCategoryArticles(
        createdAt = parsedDate,
        briefingCompactArticles = this.briefings.map {
            it.toDomain()
        }
    )
}