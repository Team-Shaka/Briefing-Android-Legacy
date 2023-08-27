package com.dev.briefing.util

import android.app.Application
import com.dev.briefing.di.dataSourceModule
import com.dev.briefing.di.networkModule
import com.dev.briefing.di.repositoryModule
import com.dev.briefing.di.viewModelModule
import org.koin.core.context.startKoin
class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(networkModule, viewModelModule, repositoryModule, dataSourceModule)
        }

    }
}
