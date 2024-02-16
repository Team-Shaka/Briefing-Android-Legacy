package com.dev.briefing.di

import com.dev.briefing.presentation.detail.ArticleDetailViewModel
import com.dev.briefing.presentation.home.HomeViewModel
import com.dev.briefing.presentation.login.SignInViewModel
import com.dev.briefing.presentation.scrap.ScrapViewModel
import com.dev.briefing.presentation.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(
        briefingRepository = get(),
        dailyAlertManager = get(),
        dailyAlertTimePreferenceHelper = get()
    ) }
    viewModel { ArticleDetailViewModel(briefingRepository = get(), scrapRepository = get(), authPreferenceHelper = get()) }
    viewModel { SignInViewModel(authRepository = get(), pushRepository = get(), authPreferenceHelper = get()) }
    viewModel { SettingViewModel(dailyAlertTimePreferenceHelper = get(), dailyAlertManager = get(), authPreferenceHelper = get(), authRepository = get()) }
    viewModel { ScrapViewModel(repository = get(), authPreferenceHelper = get())}
}