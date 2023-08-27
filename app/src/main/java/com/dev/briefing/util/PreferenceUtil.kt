package com.dev.briefing.util


import android.content.Context
import android.content.SharedPreferences
import com.dev.briefing.data.Alarm
import com.dev.briefing.data.NewsDetail
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
//import kotlinx.serialization.json.Json
class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)
}


object SharedPreferenceHelper {
    private const val PREF_NAME = "my_shared_pref"
    private const val SCRAP_ITEM = "scrap"
    private const val ALARM_TIME = "alarm"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveScrap(context: Context, items: List<NewsDetail>) {
        val json = Gson().toJson(items)
        getSharedPreferences(context).edit().putString(SCRAP_ITEM, json).apply()
    }
    fun saveAlarm(context: Context, items: Alarm) {
        val json = Gson().toJson(items)
        getSharedPreferences(context).edit().putString(ALARM_TIME, json).apply()
    }

    fun getScrap(context: Context): List<NewsDetail> {
        val json = getSharedPreferences(context).getString(SCRAP_ITEM, null)
        return if (json != null) {
            val itemType = object : TypeToken<List<NewsDetail>>() {}.type
            Gson().fromJson(json, itemType)
        } else {
            emptyList()
        }
    }
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