package com.dev.briefing.model

import com.dev.briefing.data.model.ScrapResponse

data class Scrap(
    val briefingId: Int,
    val ranks: Int,
    val title: String,
    val subtitle: String,
    val date: String,
    val gptModel: String,
    val timeOfDay: String
)

val tmpScrap = Scrap(
    briefingId = 1,
    title = "title",
    subtitle = "subtitle",
    ranks = 1,
    date = "2023-1-1",
    gptModel = "gpt-3.5-turbo",
    timeOfDay = "Morning"
)

fun ScrapResponse.toScrap(): Scrap {
    return Scrap(
        briefingId = this.briefingId,
        ranks = this.ranks,
        title = this.title,
        subtitle = this.subtitle,
        date = this.date,
        gptModel = if (this.gptModel == "gpt-3.5-turbo") "GPT-3" else "GPT-4",
        timeOfDay = if (this.timeOfDay == "MORNING") "아침" else "저녁"
    )
}