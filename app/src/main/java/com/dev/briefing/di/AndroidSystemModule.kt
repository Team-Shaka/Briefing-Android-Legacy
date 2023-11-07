package com.dev.briefing.di

import android.app.AlarmManager
import android.content.Context
import com.dev.briefing.data.datasource.AuthDataSource
import com.dev.briefing.data.datasource.AuthDataSourceImpl
import com.dev.briefing.data.datasource.BriefingDataSource
import com.dev.briefing.data.datasource.BriefingDataSourceImpl
import com.dev.briefing.data.datasource.ScrapDataSource
import com.dev.briefing.data.datasource.ScrapDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidSystemModule = module {
    single<AlarmManager> { androidContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager }
}