package com.dev.briefing.data.api

import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.GoogleRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface AuthApi {
    @POST("/members/auth/google")
    suspend fun getLoginToken(
        @Body identityToken : GoogleRequest,
    ): CommonResponse

}