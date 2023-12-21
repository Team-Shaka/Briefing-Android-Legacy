package com.dev.briefing.di

import com.dev.briefing.util.preference.AuthPreferenceHelper
import com.dev.briefing.util.preference.DailyAlertTimePreferenceHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferenceModule = module {
    factory {
        DailyAlertTimePreferenceHelper(context = androidContext())
    }

    factory {
        AuthPreferenceHelper(context = androidContext())
    }
}