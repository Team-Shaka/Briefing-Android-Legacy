package com.dev.briefing.di


import com.dev.briefing.BuildConfig
import com.dev.briefing.data.api.AuthApi
import com.dev.briefing.data.api.BriefingApi
import com.dev.briefing.data.network.NetworkInterceptor
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .addNetworkInterceptor(NetworkInterceptor())
            .addInterceptor(Interceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder().build()
                )
            })
            .build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(get())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    single<BriefingApi> {
        get<Retrofit>().create(BriefingApi::class.java)
    }
    single<AuthApi> {
        get<Retrofit>().create(AuthApi::class.java)
    }


}