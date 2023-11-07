package com.dev.briefing.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.content.Context
import android.content.Intent
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.dev.briefing.data.AlarmTime
import com.dev.briefing.receiver.DoneBroadcastReceiver
import com.dev.briefing.worker.DailyAlertWorker
import java.time.Duration
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.concurrent.TimeUnit
import kotlin.math.min

class DailyAlertManager(
    private val context: Context,
    private val alarmManager: AlarmManager
) {
    fun setDailyAlarm(hourOfDay: Int, minute: Int) {
//        AlarmManagerUtil(context).cancelAndSetAlarm(AlarmTime(hourOfDay, minute))
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        val alarmIntent = Intent(context, DoneBroadcastReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, 0, intent, FLAG_MUTABLE)
        }

//        alarmManager.setInexactRepeating(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            AlarmManager.INTERVAL_DAY,
//            alarmIntent
//        )

        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            CalendarUtil.getNextTimeInMillisFromNow(hourOfDay, minute),
            alarmIntent
        )
    }
}