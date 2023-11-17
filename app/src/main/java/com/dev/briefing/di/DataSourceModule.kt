package com.dev.briefing.di

import com.dev.briefing.data.datasource.AuthDataSource
import com.dev.briefing.data.datasource.AuthDataSourceImpl
import com.dev.briefing.data.datasource.BriefingDataSource
import com.dev.briefing.data.datasource.BriefingDataSourceImpl
import com.dev.briefing.data.datasource.ScrapDataSource
import com.dev.briefing.data.datasource.ScrapDataSourceImpl
import com.google.android.gms.auth.api.Auth
import org.koin.dsl.module

val dataSourceModule = module {
    single<BriefingDataSource> { BriefingDataSourceImpl(briefingApi = get()) }
    single<AuthDataSource> { AuthDataSourceImpl(authApi = get()) }
    single<ScrapDataSource> { ScrapDataSourceImpl(scrapApi = get()) }
}