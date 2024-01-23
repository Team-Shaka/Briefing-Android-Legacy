package com.dev.briefing.di


import com.dev.briefing.BuildConfig
import com.dev.briefing.data.api.AuthApi
import com.dev.briefing.data.api.BriefingApi
import com.dev.briefing.data.api.ScrapApi
import com.dev.briefing.data.network.AuthInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(get()))
            .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
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
    single<ScrapApi> {
        get<Retrofit>().create(ScrapApi::class.java)
    }

}