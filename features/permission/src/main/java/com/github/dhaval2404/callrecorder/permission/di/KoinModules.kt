package com.github.dhaval2404.callrecorder.permission.di

import com.github.dhaval2404.callrecorder.permission.permission_request.PermissionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for Home Module
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Jan 2020
 */
val permissionViewModelModules = module {
    /***
     * Activity View Modules
     */
    viewModel {
        PermissionViewModel(
            get()
        )
    }
}
