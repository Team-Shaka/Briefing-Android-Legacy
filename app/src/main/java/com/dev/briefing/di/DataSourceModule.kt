package com.dev.briefing.di

import com.dev.briefing.data.datasource.BriefingDataSource
import com.dev.briefing.data.datasource.BriefingDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {
    single<BriefingDataSource> { BriefingDataSourceImpl(get()) }
}