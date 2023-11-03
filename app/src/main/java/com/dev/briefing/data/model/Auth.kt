package com.dev.briefing.data.model

import com.google.gson.annotations.SerializedName

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
data class TokenRequest(
    @SerializedName("refreshToken")
    val refreshToken: String,
)

data class SingoutResponse(
    @SerializedName("quitAt")
    val quitAt: String,
)