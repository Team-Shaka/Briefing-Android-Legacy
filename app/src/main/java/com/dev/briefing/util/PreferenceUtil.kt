package com.dev.briefing.util


import android.content.Context
import android.content.SharedPreferences
import com.dev.briefing.data.Alarm
import com.dev.briefing.data.NewsContent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//import kotlinx.serialization.json.Json
class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)
}


object SharedPreferenceHelper {
    private const val PREF_NAME = "my_shared_pref"
    private const val SCRAP_DATE_ID = "scrap_with_date_id"
    private const val SCRAP_ID_NEWS = "scrap_with_id_news"
    private const val ALARM_TIME = "alarm"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
    fun addIntToKey(context: Context,key: String, intToAdd: Int) {
        val existingMap = loadDateIdMap(context)
        val newList = existingMap[key]?.toMutableList() ?: mutableListOf()
        //id값 추가, 없으면 빈 리스트에 id 값 추가
        newList.add(intToAdd)
        existingMap[key] = newList
        saveDateIdMap(context,existingMap)
    }
    fun addArticleDetail(context: Context,key: Int, value: NewsContent) {
        val existingMap = loadIdDetailMap(context)
        //id값 추가, 없으면 빈 리스트에 id 값 추가
        existingMap[key] = value
        saveIdDetailMap(context,existingMap)
    }
    fun removeIntFromKey(context: Context,key: String, valueToRemove: Int) {
        val existingMap = loadDateIdMap(context)
        val existingList = existingMap[key]?.toMutableList() ?: mutableListOf()
        existingList.remove(valueToRemove)
        existingMap[key] = existingList
        saveDateIdMap(context,existingMap)
    }
    fun removeDetilFromId(context: Context,id: Int) {
        var existingMap = loadIdDetailMap(context)
        existingMap.remove(id)
        saveIdDetailMap(context,existingMap)
    }
    private fun saveDateIdMap(context: Context, data: Map<String, List<Int>>) {
        val json = Gson().toJson(data)
        getSharedPreferences(context).edit().putString(SCRAP_DATE_ID, json).apply()
    }
    private fun saveIdDetailMap(context: Context, data: Map<Int, NewsContent>) {
        val json = Gson().toJson(data)
        getSharedPreferences(context).edit().putString(SCRAP_ID_NEWS, json).apply()
    }
    fun loadDateIdMap(context: Context): MutableMap<String, List<Int>> {
        val json = getSharedPreferences(context).getString(SCRAP_DATE_ID, null)
        return if (json != null) {
            val type = object : TypeToken<MutableMap<String, List<Int>>>() {}.type
            Gson().fromJson(json, type)

        } else {
            mutableMapOf()
        }
    }
    fun loadIdDetailMap(context: Context): MutableMap<Int, NewsContent> {
        val json = getSharedPreferences(context).getString(SCRAP_ID_NEWS, null)
        return if (json != null) {
            val type = object : TypeToken<MutableMap<Int, NewsContent>>() {}.type
            Gson().fromJson(json, type)

        } else {
            mutableMapOf()
        }
    }


    //key는 date
    fun savePreference(context: Context, key: String, idList: List<Int>) {

        val json = Gson().toJson(idList)
        getSharedPreferences(context).edit().putString(SCRAP_DATE_ID, json).apply()
    }
    fun savePreference(context: Context, id: String,articleDetail: NewsContent) {
        val json = Gson().toJson(articleDetail)
        getSharedPreferences(context).edit().putString(id, json).apply()
    }

    fun savePreference(context: Context, items: Alarm) {
        val json = Gson().toJson(items)
        getSharedPreferences(context).edit().putString(ALARM_TIME, json).apply()
    }
//    //1.Scrap 페이지에 데이터를 가져올때
//    fun getScrap(context: Context): List<NewsDetail> {
//        val json = getSharedPreferences(context).getString(SCRAP_ITEM, null)
//        return if (json != null) {
//            val itemType = object : TypeToken<List<NewsDetail>>() {}.type
//            Gson().fromJson(json, itemType)
//        } else {
//            emptyList()
//        }
//    }
    fun getAlarm(context: Context): Alarm {
        val json = getSharedPreferences(context).getString(ALARM_TIME, null)
        return if (json != null) {
            val itemType = object : TypeToken<Alarm>() {}.type
            Gson().fromJson(json, itemType)
        } else {
           Alarm(hour = 9, minute = 0)
        }
    }
}