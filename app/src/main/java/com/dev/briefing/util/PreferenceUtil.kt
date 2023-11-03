package com.dev.briefing.util


import android.content.Context
import android.content.SharedPreferences
import com.dev.briefing.data.Alarm
import com.dev.briefing.data.NewsContent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SharedPreferenceHelper(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    fun putSharedPreference(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }
    fun putSharedPreference(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getSharedPreference(key: String, defaultValue: Int): Int {
        return prefs.getInt(key, defaultValue)
    }
    fun getSharedPreference(key: String, defaultValue: String): String {
        return prefs.getString(key, defaultValue).toString()
    }
    fun removeSharedPreference(key: String) {
        prefs.edit().remove(key).apply()
    }

    //key는 date
    fun savePreference( key: String, idList: List<Int>) {

        val json = Gson().toJson(idList)
        prefs.edit().putString(SCRAP_DATE_ID, json).apply()
    }
    fun savePreference( id: String,articleDetail: NewsContent) {
        val json = Gson().toJson(articleDetail)
        prefs.edit().putString(id, json).apply()
    }

    fun savePreference(items: Alarm) {
        val json = Gson().toJson(items)
        prefs.edit().putString(ALARM_TIME, json).apply()
    }

    fun getAlarm(): Alarm {
        val json = prefs.getString(ALARM_TIME, null)
        return if (json != null) {
            val itemType = object : TypeToken<Alarm>() {}.type
            Gson().fromJson(json, itemType)
        } else {
           Alarm(hour = 9, minute = 0)
        }
    }
}