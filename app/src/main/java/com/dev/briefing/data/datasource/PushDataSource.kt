package com.dev.briefing.data.datasource

import com.dev.briefing.data.model.FcmTokenRequest

interface PushDataSource {

    suspend fun subscribePushAlarm(fcmTokenRequest: FcmTokenRequest): String

    suspend fun unsubscribePushAlarm(fcmTokenRequest: FcmTokenRequest): String

}