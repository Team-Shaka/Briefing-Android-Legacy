package com.dev.briefing.data.datasource

import com.dev.briefing.data.api.PushApi
import com.dev.briefing.data.model.FcmTokenRequest

class PushDataSourceImpl(private val pushApi: PushApi) : PushDataSource {

    override suspend fun subscribePushAlarm(fcmTokenRequest: FcmTokenRequest): String {
        return pushApi.subscribePushAlarm(fcmTokenRequest).result
    }

    override suspend fun unsubscribePushAlarm(fcmTokenRequest: FcmTokenRequest): String {
        return pushApi.unsubscribePushAlarm(fcmTokenRequest).result
    }

}