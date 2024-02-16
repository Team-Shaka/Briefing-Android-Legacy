package com.dev.briefing.data.respository

interface PushRepository {

    suspend fun subscribePushAlarm(fcmToken: String): String

    suspend fun unsubscribePushAlarm(fcmToken: String): String

}