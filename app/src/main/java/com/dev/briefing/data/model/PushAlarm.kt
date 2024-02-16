package com.dev.briefing.data.model

import com.google.gson.annotations.SerializedName

data class FcmTokenRequest(
    @SerializedName("token")
    val token: String
)
