package com.dev.briefing.data.api

import com.dev.briefing.data.model.MemberDeleteResponse
import com.dev.briefing.data.model.SocialLoginRequest
import com.dev.briefing.data.model.SocialLoginResponse
import com.dev.briefing.data.model.TokenRequest
import com.dev.briefing.data.model.response.common.CommonResponse
import retrofit2.Response
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
    suspend fun deleteMember(
        @Path("memberId") memberId : Int,
    ): CommonResponse<MemberDeleteResponse>

    @POST("members/auth/token")
    suspend fun getAccessToken(
        @Body refreshToken : TokenRequest
    ): Response<CommonResponse<SocialLoginResponse>>
}