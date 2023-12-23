package com.dev.briefing.model

import com.google.gson.annotations.SerializedName

data class RelatedArticle(
    val id: Int,
    val press: String,
    val title: String,
    val url: String
)