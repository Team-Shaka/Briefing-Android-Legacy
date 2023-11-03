package com.dev.briefing.util.preference

import android.content.Context
import com.dev.briefing.data.AlarmTime
import com.dev.briefing.util.MainApplication.Companion.prefs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DailyAlertTimePreferenceHelper(val context: Context) {

    fun getAlarmTime(): AlarmTime {
        val json = prefs
            .getSharedPreference(KEY_DAILY_ALARM_TIME, "")
        return if (json.isNotEmpty()) {
            val itemType = object : TypeToken<AlarmTime>() {}.type
            Gson().fromJson(json, itemType)
        } else {
            AlarmTime(hour = 9, minute = 0)
        }
    }

    fun saveAlarmTime(items: AlarmTime) {
        val json = Gson().toJson(items)
        prefs.putSharedPreference(KEY_DAILY_ALARM_TIME, json)
    }

    companion object {
        private const val KEY_DAILY_ALARM_TIME = "DAILY_ALARM_TIME"
    }

}