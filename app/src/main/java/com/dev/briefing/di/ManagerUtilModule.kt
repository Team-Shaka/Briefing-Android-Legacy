package com.dev.briefing.di

import com.dev.briefing.util.dailyalert.DailyAlertManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val managerUtilModule = module {
    factory {
        DailyAlertManager(androidContext(), get())
    }
}