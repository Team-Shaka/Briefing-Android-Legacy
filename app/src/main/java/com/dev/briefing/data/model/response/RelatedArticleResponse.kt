package com.dev.briefing.data.model.response

import com.dev.briefing.model.RelatedArticle
import com.google.gson.annotations.SerializedName

data class RelatedArticleResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("press") val press: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String
)

fun RelatedArticleResponse.toDomain(): RelatedArticle {
    return RelatedArticle(
        id = this.id,
        press = this.press,
        title = this.title,
        url = this.url
    )
}