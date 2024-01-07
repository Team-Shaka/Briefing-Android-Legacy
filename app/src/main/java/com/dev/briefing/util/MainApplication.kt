package com.dev.briefing.util

import android.app.Application
import com.dev.briefing.BuildConfig
import com.dev.briefing.di.androidSystemModule
import com.dev.briefing.di.dataSourceModule
import com.dev.briefing.di.managerUtilModule
import com.dev.briefing.di.networkModule
import com.dev.briefing.di.preferenceModule
import com.dev.briefing.di.repositoryModule
import com.dev.briefing.di.viewModelModule
import com.google.android.gms.ads.RequestConfiguration
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // initialize log library
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

        RequestConfiguration.Builder().setTestDeviceIds(listOf(BuildConfig.ADMOB_TEST_DEVICE_1))

        startKoin {
            androidContext(this@MainApplication)
            modules(
                networkModule,
                viewModelModule,
                repositoryModule,
                dataSourceModule,
                managerUtilModule,
                preferenceModule,
                androidSystemModule,
            )
        }
    }
}
