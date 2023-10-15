package com.dev.briefing.data.api

import com.dev.briefing.data.model.CommonResponse
import com.dev.briefing.data.model.GoogleRequest
import com.dev.briefing.data.model.GoogleSocialResponse
import com.dev.briefing.data.model.SingoutResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApi {
    @POST("/members/auth/google")
    suspend fun getLoginToken(
        @Body identityToken : GoogleRequest,
    ): CommonResponse<GoogleSocialResponse>

    @DELETE("/members/{memberId}")
    suspend fun signOut(
        @Path("memberId") memberId : Int,
    ): CommonResponse<SingoutResponse>
}