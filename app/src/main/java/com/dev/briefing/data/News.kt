package com.dev.briefing.data

data class News(
    val id:Int,
    val rank:Int,
    val title:String,
    val subtitle:String,
)
data class NewsDetail(
    val id:Int,
    val rank:Int,
    val title:String,
    val date: String,
    val subtitle:String,
)

data class NewsContent(
    val rank:Int,
    val title:String,
    val subtitle:String,
)

data class NewsLink(
    val press:String,
    val title:String,
    val url:String,
)