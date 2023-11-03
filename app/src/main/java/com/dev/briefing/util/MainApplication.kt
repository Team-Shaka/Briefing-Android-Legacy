package com.dev.briefing.util

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import com.dev.briefing.BuildConfig
import com.dev.briefing.di.dataSourceModule
import com.dev.briefing.di.managerUtilModule
import com.dev.briefing.di.networkModule
import com.dev.briefing.di.preferenceModule
import com.dev.briefing.di.repositoryModule
import com.dev.briefing.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(
                networkModule,
                viewModelModule,
                repositoryModule,
                dataSourceModule,
                managerUtilModule,
                preferenceModule
            )
        }


    }
}
