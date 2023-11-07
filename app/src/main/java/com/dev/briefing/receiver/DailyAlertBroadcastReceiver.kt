package com.dev.briefing.receiver

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dev.briefing.BuildConfig
import com.dev.briefing.R
import com.dev.briefing.presentation.home.HomeActivity
import com.dev.briefing.util.ALARM_TAG
import com.dev.briefing.util.dailyalert.DailyAlertManager
import com.dev.briefing.util.preference.DailyAlertTimePreferenceHelper

class DailyAlertBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("DailyAlertLog", "onReceive() call")
        if (Intent.ACTION_BOOT_COMPLETED != intent.action) {
            showNotification(context)
        } else {
            Log.d("DailyAlertLog", "ACTION_BOOT_COMPLETED")
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        DailyAlertTimePreferenceHelper(context).getAlarmTime().also {
            DailyAlertManager(context, alarmManager).setDailyAlarm(it.hour, it.minute)
        }
    }

    private fun showNotification(context: Context) {
        val notificationManager = NotificationManagerCompat.from(context)
        val notificationChannel = NotificationChannel(
            BuildConfig.NOTIFICATION_CHANNEL_ID,
            "Briefing",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationChannel.description = "A channel used to send brief remind"
        notificationManager.createNotificationChannel(notificationChannel)
        Log.d(ALARM_TAG, "2 notification 채널 생성")

        val intent = Intent(context, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, BuildConfig.NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Briefieng")
            .setAutoCancel(true)
            .setContentText("오늘의 새 Briefing을 확인해보세요")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(ALARM_TAG, "3 권한 허용이 거부되어 있음 ")
            Toast.makeText(context, "알림 권한을 허용해주세요", Toast.LENGTH_SHORT).show()
        }
        notificationManager.notify(1, notification)
    }
}