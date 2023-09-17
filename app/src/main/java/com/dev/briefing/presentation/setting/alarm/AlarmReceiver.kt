package com.dev.briefing.presentation.setting.alarm

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dev.briefing.BuildConfig
import com.dev.briefing.R
import com.dev.briefing.util.ALARM_TAG

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try {
            Log.d(ALARM_TAG,"1 alarm receiver 시작")
            showNotification(context)
        }catch(e:Exception){
            Log.d(ALARM_TAG,e.toString())
        }

    }
    private fun showNotification(context: Context){

        val notificationManager = NotificationManagerCompat.from(context)
        val notificationChannel  = NotificationChannel(BuildConfig.NOTIFICATION_CHANNEL_ID, "Briefing", NotificationManager.IMPORTANCE_DEFAULT)
        notificationChannel.description = "A channel used to send brief remind"
        notificationManager.createNotificationChannel(notificationChannel)
        Log.d(ALARM_TAG,"2 notification 채널 생성")

        val notification = NotificationCompat.Builder(context, BuildConfig.NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Briefieng")
            .setContentText("오늘의 새 Briefing을 확인해보세요")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(ALARM_TAG,"3 권한 허용")

//            notificationManager.notify(1, notification)

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(1, notification)

    }

}