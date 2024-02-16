package com.dev.briefing.data.respository

import com.dev.briefing.data.datasource.PushDataSource
import com.dev.briefing.data.model.FcmTokenRequest

class PushRepositoryImpl(private val pushDataSource: PushDataSource) :
    PushRepository {
    override suspend fun subscribePushAlarm(fcmToken: String): String {
        return pushDataSource.subscribePushAlarm(
            FcmTokenRequest(fcmToken)
        )
    }

    override suspend fun unsubscribePushAlarm(fcmToken: String): String {
        return pushDataSource.unsubscribePushAlarm(
            FcmTokenRequest(fcmToken)
        )
    }
}