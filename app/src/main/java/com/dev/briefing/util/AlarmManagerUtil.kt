package com.dev.briefing.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.dev.briefing.data.AlarmTime
import com.dev.briefing.receiver.DailyAlertBroadcastReceiver

class AlarmManagerUtil(val context: Context) {

    companion object {
        const val WRITE_DONE_LIST_ALARM = "WRITE_DONE_LIST_ALARM"
        private var alarmManager: AlarmManager? = null
    }

    private fun alarmManager(): AlarmManager {
        if (alarmManager == null) {
            alarmManager =
                context.applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        }
        return alarmManager!!
    }

    fun cancelAndSetAlarm(alarm: AlarmTime) {
        val pendingIntent = getAlarmPendingIntent(alarm)
//        pendingIntent.cancel()
        alarmManager().cancel(pendingIntent)

        val intent = Intent(context.applicationContext, DailyAlertBroadcastReceiver::class.java)
        val t = PendingIntent.getBroadcast(
            context.applicationContext,
            1,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE,
        )

        Log.d("알람", "cancelAndSetAlarm 취소 ${t == null} / ${alarm.hour} ${alarm.minute}")

        alarmManager().setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            CalendarUtil.getNextTimeInMillisFromNow(alarm.hour, alarm.minute),
            getAlarmPendingIntent(alarm)
        )

        Log.d("알람", "cancelAndSetAlarm ${alarm.hour} ${alarm.minute}")
    }


    private fun getAlarmPendingIntent(alarm: AlarmTime): PendingIntent {
        val intent = Intent(context.applicationContext, DailyAlertBroadcastReceiver::class.java)

        return PendingIntent.getBroadcast(
            context.applicationContext,
            1,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }
}