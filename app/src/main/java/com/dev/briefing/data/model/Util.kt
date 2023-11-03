package com.dev.briefing.data.model

import com.google.gson.annotations.SerializedName

data class CommonResponse<T>(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: T,
)