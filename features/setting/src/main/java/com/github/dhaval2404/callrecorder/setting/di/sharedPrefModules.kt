package com.github.dhaval2404.callrecorder.setting.di

import android.content.Context
import androidx.preference.PreferenceManager
import com.github.dhaval2404.base.shared_pref.SharedPrefManager
import com.github.dhaval2404.callrecorder.setting.data.SettingPref
import com.github.dhaval2404.callrecorder.setting.data.SettingPrefImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

/**
 * @author Dhaval Patel
 * @version 1.0
 * @since 26 Jan 2019
 */
val settingSharedPrefModules = module {

    factory<SharedPrefManager>(named("setting")) {
        val pref = PreferenceManager.getDefaultSharedPreferences(get<Context>())
        SharedPrefManager(pref)
    }

    single<SettingPref> {
        SettingPrefImpl(
            get(),
            get(named("setting"))
        )
    }
}
