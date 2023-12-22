package com.dev.briefing.data.network

import com.dev.briefing.data.api.AuthApi
import com.dev.briefing.data.model.TokenRequest
import com.dev.briefing.data.respository.AuthRepository
import com.dev.briefing.util.preference.AuthPreferenceHelper
import com.orhanobut.logger.Logger
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException
import org.koin.compose.koinInject
import org.koin.java.KoinJavaComponent.inject


class AuthInterceptor(
    private val authPreferenceHelper: AuthPreferenceHelper
) : Interceptor {

    private val authApi: AuthApi by inject(AuthApi::class.java)
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
            Logger.d("response code : ${response.code} ${response.message}")
        } else {
            Logger.e("response code : ${response.code} ${response.peekBody(2048).string()}")
        }

        if (response.code == 401 && token != null) {
            Logger.d("refresh token")

            val newAccessToken = runBlocking { callRefreshTokenAPI() }
            val newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .build()
            return chain.proceed(newRequest)
        }

        return response
    }

    private suspend fun callRefreshTokenAPI(): String {
        val refreshToken = authPreferenceHelper.getRefreshToken()!!
        val response = authApi.getAccessToken(TokenRequest(refreshToken = refreshToken))
        authPreferenceHelper.saveToken(
            response.result.accessToken,
            response.result.refreshToken
        )
        Logger.d("new access token : ${response.result.accessToken}, new refresh token : ${response.result.refreshToken}")
        return response.result.accessToken
    }
}