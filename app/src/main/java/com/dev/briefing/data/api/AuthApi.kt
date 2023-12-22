package com.dev.briefing.data.api

import com.dev.briefing.data.model.response.common.CommonResponse
import com.dev.briefing.data.model.SocialLoginRequest
import com.dev.briefing.data.model.SocialLoginResponse
import com.dev.briefing.data.model.SingoutResponse
import com.dev.briefing.data.model.TokenRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApi {
    @POST("members/auth/{provider}")
    suspend fun signInWithSocialProvider(
        @Path("provider") provider : String,
        @Body identityToken : SocialLoginRequest,
    ): CommonResponse<SocialLoginResponse>

    @DELETE("members/{memberId}")
    suspend fun withdrawal(
        @Path("memberId") memberId : Int,
    ): CommonResponse<SingoutResponse>

    @POST("members/auth/token")
    suspend fun getAccessToken(
        @Body refreshToken : TokenRequest,
    ): CommonResponse<SocialLoginResponse>
}