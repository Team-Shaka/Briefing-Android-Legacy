package com.dev.briefing.util.preference

import android.content.Context
import com.dev.briefing.data.AlarmTime
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DailyAlertTimePreferenceHelper(val context: Context) {

    private val preference = SharedPreferenceHelper.getSharedPreferences(context)
    fun getAlarmTime(): AlarmTime {
        val json = preference
            .getString(KEY_DAILY_ALARM_TIME, null)
        return if (json != null) {
            val itemType = object : TypeToken<AlarmTime>() {}.type
            Gson().fromJson(json, itemType)
        } else {
            AlarmTime(hour = 9, minute = 0)
        }
    }

    fun saveAlarmTime(items: AlarmTime) {
        val json = Gson().toJson(items)
        SharedPreferenceHelper.getSharedPreferences(context)
            .edit().putString(KEY_DAILY_ALARM_TIME, json).apply()
    }

    companion object {
        private const val KEY_DAILY_ALARM_TIME = "DAILY_ALARM_TIME"
    }

}