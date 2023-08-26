package com.dev.briefing.data.network
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder().addHeader("Content-Type", "application/json")
        return chain.proceed(builder.build())
    }
}