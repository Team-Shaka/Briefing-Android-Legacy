package com.dev.briefing.data

data class News(
    val rank:Int,
    val title:String,
    val subtitle:String,
)

data class NewsLink(
    val press:String,
    val title:String,
    val url:String,
)