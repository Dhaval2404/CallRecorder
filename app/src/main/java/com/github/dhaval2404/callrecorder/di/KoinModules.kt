package com.github.dhaval2404.callrecorder.di

import com.github.dhaval2404.callrecorder.splash_screen.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin ViewModel Modules
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 22 January 2020
 */
val appViewModelModules = module {
    /***
     * Activity View Modules
     */
    viewModel { SplashViewModel(get()) }
}
