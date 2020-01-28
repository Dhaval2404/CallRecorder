package com.github.dhaval2404.callrecorder.home.di

import com.github.dhaval2404.callrecorder.home.screen.inbox.InboxViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for Home Module
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Jan 2020
 */
val homeViewModelModules = module {
    /***
     * Activity View Modules
     */
    viewModel { InboxViewModel(get()) }
}
