package com.capstone.fall_guard.di

import com.capstone.fall_guard.data.repo.MainRepository
import com.capstone.fall_guard.ui.main.home.HomeViewModel
import com.capstone.fall_guard.ui.main.metrics.MetricsViewModel
import com.capstone.fall_guard.ui.main.profile.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val repositoryModules = module {
    factory { MainRepository.getInstance(androidContext()) }
}

val viewModelModules = module {
    viewModel { HomeViewModel(get()) }
    viewModel { MetricsViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}