package com.dev.briefing.di

import com.dev.briefing.data.datasource.AuthDataSource
import com.dev.briefing.data.datasource.AuthDataSourceImpl
import com.dev.briefing.data.datasource.BriefingDataSource
import com.dev.briefing.data.datasource.BriefingDataSourceImpl
import com.dev.briefing.data.datasource.PushDataSource
import com.dev.briefing.data.datasource.PushDataSourceImpl
import com.dev.briefing.data.datasource.ScrapDataSource
import com.dev.briefing.data.datasource.ScrapDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {
    single<BriefingDataSource> { BriefingDataSourceImpl(briefingApi = get()) }
    single<AuthDataSource> { AuthDataSourceImpl(authApi = get()) }
    single<ScrapDataSource> { ScrapDataSourceImpl(scrapApi = get(), authApi = get()) }
    single<PushDataSource> { PushDataSourceImpl(pushApi = get()) }
}