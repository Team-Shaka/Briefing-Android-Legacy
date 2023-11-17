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
        repository = get(),
        dailyAlertManager = get(),
        dailyAlertTimePreferenceHelper = get()
    ) }
    viewModel { (id: Int) -> ArticleDetailViewModel(repository = get(), id = id) }
    viewModel { SignInViewModel(repository = get()) }
    viewModel { SettingViewModel(dailyAlertTimePreferenceHelper = get(), dailyAlertManager = get()) }
    viewModel { ScrapViewModel(repository = get())}
}