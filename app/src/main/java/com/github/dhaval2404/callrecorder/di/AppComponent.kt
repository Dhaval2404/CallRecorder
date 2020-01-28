package com.github.dhaval2404.callrecorder.di

import com.github.dhaval2404.callrecorder.callrecord.di.callRecordModule
import com.github.dhaval2404.callrecorder.home.di.homeViewModelModules
import com.github.dhaval2404.callrecorder.permission.di.permissionViewModelModules
import com.github.dhaval2404.callrecorder.setting.di.settingSharedPrefModules

/**
 * Combine all dependencies
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 22 January 2020
 */
val appComponent = listOf(
    // app module
    appViewModelModules,

    homeViewModelModules,

    callRecordModule,

    settingSharedPrefModules,

    permissionViewModelModules
)
