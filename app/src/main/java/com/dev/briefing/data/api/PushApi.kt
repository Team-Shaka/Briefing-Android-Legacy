package com.dev.briefing.data.api

import com.dev.briefing.data.model.FcmTokenRequest
import com.dev.briefing.data.model.response.common.CommonResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface PushApi {

    @POST("pushs/subscribe")
    suspend fun subscribePushAlarm(
        @Body fcmTokenRequest: FcmTokenRequest
    ): CommonResponse<String>

    @POST("pushs/unsubscribe")
    suspend fun unsubscribePushAlarm(
        @Body fcmTokenRequest: FcmTokenRequest
    ): CommonResponse<String>

}