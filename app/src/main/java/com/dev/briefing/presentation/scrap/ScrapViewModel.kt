package com.dev.briefing.presentation.scrap

import android.content.Context
import androidx.lifecycle.ViewModel
import com.dev.briefing.data.NewsDetail
import com.dev.briefing.util.preference.SharedPreferenceHelper
import java.time.format.DateTimeFormatter

class ScrapViewModel : ViewModel() {
    //1.  List<date=String>를 반환
    //2.  String - List<NewsDetail>를 반환
    fun getScrapData(context: Context):MutableMap<String, List<NewsDetail>> {
        var newsList: MutableMap<String, List<NewsDetail>> = mutableMapOf()
        val scrapDateIdMap = SharedPreferenceHelper.loadDateIdMap(context)
        val scrapIdDetailMap = SharedPreferenceHelper.loadIdDetailMap(context)



        for ((key, value) in scrapDateIdMap) {
            var tmpNewsList:MutableList<NewsDetail> = mutableListOf()
            for (id in value){
                var tmpDetail = scrapIdDetailMap[id]
                tmpNewsList.add( NewsDetail(
                    id = id, rank = tmpDetail?.rank ?: 0, title = tmpDetail?.title ?: "",
                    date = key.format(DateTimeFormatter.ofPattern("yy.MM.dd"))?:"", subtitle = tmpDetail?.subtitle ?: ""))
            }
            newsList[key] = tmpNewsList
        }
        return newsList

    }
}