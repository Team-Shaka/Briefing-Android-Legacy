package com.dev.briefing.di

import com.dev.briefing.data.datasource.AuthDataSource
import com.dev.briefing.data.datasource.AuthDataSourceImpl
import com.dev.briefing.data.datasource.BriefingDataSource
import com.dev.briefing.data.datasource.BriefingDataSourceImpl
import com.google.android.gms.auth.api.Auth
import org.koin.dsl.module

val dataSourceModule = module {
    single<BriefingDataSource> { BriefingDataSourceImpl(get()) }
    single<AuthDataSource> { AuthDataSourceImpl(get()) }
}