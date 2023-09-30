package com.dev.briefing.data.api

import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.GoogleRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("/member/auth/google")
    suspend fun getLoginToken(
        @Body identityToken : GoogleRequest,
    ): CommonResponse
}