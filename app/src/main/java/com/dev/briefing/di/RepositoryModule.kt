package com.dev.briefing.di

import com.dev.briefing.data.respository.AuthRepository
import com.dev.briefing.data.respository.AuthRepositoryImpl
import com.dev.briefing.data.respository.BriefingRepository
import com.dev.briefing.data.respository.BriefingRepositoryImpl
import com.dev.briefing.data.respository.ScrapRepository
import com.dev.briefing.data.respository.ScrapRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<BriefingRepository> { BriefingRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<ScrapRepository> { ScrapRepositoryImpl(get()) }
}