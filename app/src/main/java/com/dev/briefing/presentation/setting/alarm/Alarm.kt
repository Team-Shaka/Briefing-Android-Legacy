package com.dev.briefing.presentation.setting.alarm

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import com.dev.briefing.R
import java.text.SimpleDateFormat
import java.util.*
@Composable
fun setAlarmTime(alarmTime: Long){
    val context = LocalContext.current
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, alarmIntent)

    val notificationId = 0
    val notification = NotificationCompat.Builder(context, "channelId")
        .setContentTitle("Alarm")
        .setContentText("Alarm is set for ${SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(alarmTime))}")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()

    notificationManager.notify(notificationId, notification)
}