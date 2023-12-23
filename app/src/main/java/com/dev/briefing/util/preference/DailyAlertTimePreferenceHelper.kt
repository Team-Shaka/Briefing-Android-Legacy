package com.dev.briefing.util.preference

import android.content.Context
import android.content.SharedPreferences
import com.dev.briefing.data.AlarmTime
import com.dev.briefing.util.PREF_NAME
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DailyAlertTimePreferenceHelper(val context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()

    fun getAlarmTime(): AlarmTime {
        val json = prefs.getString(KEY_DAILY_ALARM_TIME, "")
        return if (json?.isNotEmpty() == true) {
            val itemType = object : TypeToken<AlarmTime>() {}.type
            Gson().fromJson(json, itemType)
        } else {
            AlarmTime(hour = 9, minute = 0)
        }
    }

    fun saveAlarmTime(items: AlarmTime) {
        val json = Gson().toJson(items)
        editor.putString(KEY_DAILY_ALARM_TIME, json)
    }

    companion object {
        private const val KEY_DAILY_ALARM_TIME = "DAILY_ALARM_TIME"
    }
}