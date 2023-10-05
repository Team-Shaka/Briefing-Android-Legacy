package com.dev.briefing.di

import com.dev.briefing.presentation.detail.ArticleDetailViewModel
import com.dev.briefing.presentation.home.HomeViewModel
import com.dev.briefing.presentation.login.SignInViewModel
import com.dev.briefing.presentation.scrap.ScrapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel {(id: Int) -> ArticleDetailViewModel(get(),id) }
    viewModel { SignInViewModel(get()) }
    viewModel { ScrapViewModel(get())}
}