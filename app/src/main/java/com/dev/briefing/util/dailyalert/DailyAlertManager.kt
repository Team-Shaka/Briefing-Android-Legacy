package com.dev.briefing.util.dailyalert

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import android.util.Log
import com.dev.briefing.receiver.DailyAlertBroadcastReceiver
import com.dev.briefing.util.CalendarUtil

class DailyAlertManager(
    private val context: Context,
    private val alarmManager: AlarmManager
) {
    fun setDailyAlarm(hourOfDay: Int, minute: Int) {
        Log.d("DailyAlertLog", "regist request: $hourOfDay:$minute")

        val alarmIntent = Intent(context, DailyAlertBroadcastReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, FLAG_MUTABLE)
        }

        alarmManager.cancel(alarmIntent)
        Log.d("DailyAlertLog", "alarm canceled")

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            CalendarUtil.getNextTimeInMillisFromNow(hourOfDay, minute),
            alarmIntent
        )
        Log.d("DailyAlertLog", "alarm registered at $hourOfDay:$minute")
    }
}