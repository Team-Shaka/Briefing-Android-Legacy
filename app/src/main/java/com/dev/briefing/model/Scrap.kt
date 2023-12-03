package com.dev.briefing.model

import com.dev.briefing.data.model.ScrapResponse

data class Scrap(
    val briefingId: Int,
    val ranks: Int,
    val title: String,
    val subtitle: String,
    val date: String
)
val tmpScrap = Scrap(
    briefingId = 1,
    title = "title",
    subtitle = "subtitle",
    ranks = 1,
    date = "2023-1-1"
)
fun ScrapResponse.toScrap(): Scrap {
    return Scrap(
        briefingId = this.briefingId,
        ranks = this.ranks,
        title = this.title,
        subtitle = this.subtitle,
        date = this.date
    )
}