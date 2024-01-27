package com.dev.briefing.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.dev.briefing.R
import com.dev.briefing.presentation.login.SignInActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.orhanobut.logger.Logger

class FCMService : FirebaseMessagingService() {

    private val CHANNEL_ID = "notification_remote_channel"

    private lateinit var notificationManager: NotificationManager

    // Token 생성
    override fun onNewToken(token: String) {
        Logger.d("new Token: $token")
    }

    // foreground 메세지 수신시 동작 설정
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Logger.d("From: " + remoteMessage.from)

        // 받은 remoteMessage의 값 출력해보기. 데이터메세지 / 알림메세지
        val messageData = "${remoteMessage.notification?.let {
            "${it.title} ${it.body}"
        }}"
        Logger.d("Message data : $messageData")
        Logger.d("Message notification : ${remoteMessage.notification?.imageUrl}")

        if (messageData.isNotEmpty()) {
            //알림 생성
            sendNotification(remoteMessage)
        } else {
            Logger.e("data가 비어있습니다. 메시지를 수신하지 못했습니다.")
        }
    }

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_HIGH

        val channel =
            NotificationChannel(CHANNEL_ID, "notification_channel", importance)

        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // 메세지 알림 생성
    private fun sendNotification(remoteMessage: RemoteMessage) {
        val pendingIntent = getFcmPendingIntent(this)

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.mipmap.ic_launcher) // 아이콘 설정
            .setContentTitle(remoteMessage.notification?.title) // 제목
            .setContentText(remoteMessage.notification?.body) // 메시지 내용
            .setAutoCancel(true) // 알람클릭시 삭제여부
            .setDefaults(Notification.DEFAULT_ALL) // 진동, 소리, 불빛 설정
            .setPriority(NotificationCompat.PRIORITY_HIGH) // 헤드업 알림
            .setContentIntent(pendingIntent) // 알림 실행 시 Intent

        createNotificationChannel()
        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }

    private fun getFcmPendingIntent(context: Context): PendingIntent {
        val intent = Intent(context, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        return PendingIntent.getActivity(
            context,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

}