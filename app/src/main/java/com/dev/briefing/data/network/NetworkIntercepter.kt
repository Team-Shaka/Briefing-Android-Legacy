package com.dev.briefing.data.network

import android.util.Log
import com.dev.briefing.util.JWT_TOKEN
import com.dev.briefing.util.MainApplication.Companion.prefs
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.IOException

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder().addHeader("content-type", "application/json")
        return chain.proceed(builder.build())
    }
}

class AddCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        // Preference에서 jwt를 가져오는 작업을 수행
        val cookie: String = prefs.getSharedPreference(JWT_TOKEN, "")
        if(cookie != null){
            builder.addHeader("Authorization", "Bearer ${cookie}")
        }else{
            Log.d("Cookie", "Cookie is null")
        }

        // Web,Android,iOS 구분을 위해 User-Agent세팅
        builder.removeHeader("User-Agent").addHeader("User-Agent", "Android")
        return chain.proceed(builder.build())
    }
}