package com.github.dhaval2404.callrecorder

import android.app.Application
import com.facebook.stetho.Stetho
import com.github.dhaval2404.base.extension.setTheme
import com.github.dhaval2404.callrecorder.di.appComponent
import com.github.dhaval2404.callrecorder.setting.data.SettingPref
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin

/**
 * App Setup
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 22 January 2020
 */
class CallRecorderApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        initKoin()

        initLogger()

        // Stetho
        Stetho.initializeWithDefaults(this)

        setTheme()
    }

    private fun initKoin() {
        // start Koin!
        startKoin {

            // Android context
            androidContext(this@CallRecorderApp)

            // modules
            modules(appComponent)
        }
    }

    private fun initLogger() {
        if (!BuildConfig.DEBUG) return

        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false) // (Optional) Whether to show thread info or not. Default true
            .methodCount(2) // (Optional) How many method line to show. Default 2
            .methodOffset(0) // (Optional) Hides internal method calls up to offset. Default 5
            .tag(getString(R.string.app_name)) // (Optional) Global tag for every log. Default PRETTY_LOGGER
            .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }

    private fun setTheme() {
        val settingPref: SettingPref = get()
        setTheme(settingPref.isDarkTheme())
    }
}
