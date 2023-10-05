package com.dev.briefing.data.model

import com.google.gson.annotations.SerializedName
data class CommonResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: GoogleSocialResponse,
)
data class GoogleSocialResponse(
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String,
)

data class GoogleRequest(
    @SerializedName("identityToken")
    val identityToken: String,
)