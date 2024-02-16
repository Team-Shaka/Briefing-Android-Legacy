package com.dev.briefing.data.network

import android.util.Log
import com.dev.briefing.BuildConfig.BASE_URL
import com.dev.briefing.data.api.AuthApi
import com.dev.briefing.data.model.TokenRequest
import com.dev.briefing.util.preference.AuthPreferenceHelper
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.orhanobut.logger.Logger
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AuthInterceptor(
    private val authPreferenceHelper: AuthPreferenceHelper
) : Interceptor {

    private val gson = Gson()

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val token: String? = authPreferenceHelper.getAccessToken()

        val response = chain.proceed(
            originalRequest.newBuilder()
                .header("User-Agent", "Android")
                .header("content-type", "application/json")
                .apply {
                    if (token != null) {
                        header("Authorization", "Bearer $token")
                        Logger.d("request token : $token")
                    }
                }.build()
        )

        if (response.code == 200 || response.code == 201) {
            Logger.d("response code : ${response.code} ${response.peekBody(2048).string()}")
        } else {
            Logger.e("response code : ${response.code} ${response.peekBody(2048).string()}")
        }


        if (response.code == 401 && token != null) {
            val responseBodyString = response.peekBody(2048).string()

            val jsonObject = gson.fromJson(responseBodyString, JsonObject::class.java)
            val code = jsonObject["code"].asString

            if (code == "AUTH004") {
                Logger.d("request : $originalRequest")
                Logger.d("token expired")

                val newAccessToken = runBlocking { callRefreshTokenAPI() }
                val newRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer $newAccessToken")
                    .build()
                return chain.proceed(newRequest)
            }
        }

        return response
    }

    private suspend fun callRefreshTokenAPI(): String {
        val refreshToken = authPreferenceHelper.getRefreshToken()!!

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(AuthApi::class.java)

        return try {
            val response = service.getAccessToken(TokenRequest(refreshToken))
            if (response.isSuccessful) {
                val accessToken = response.body()?.result?.accessToken ?: ""
                val refreshToken = response.body()?.result?.refreshToken ?: ""
                authPreferenceHelper.saveToken(accessToken, refreshToken)
                Logger.d("new access token : $accessToken, new refresh token : $refreshToken")
                accessToken
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                Logger.e("Access token refresh failed : $errorMessage")
                authPreferenceHelper.clearToken()
                ""
            }
        } catch (e: Exception) {
            Log.d("debugging", "Failed to refresh access token: $e")
            Logger.e("Failed to refresh access token: $e")
            authPreferenceHelper.clearToken()
            ""
        }
    }

}