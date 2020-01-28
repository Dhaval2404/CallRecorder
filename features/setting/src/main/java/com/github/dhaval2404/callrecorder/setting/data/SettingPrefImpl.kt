package com.github.dhaval2404.callrecorder.setting.data

import android.content.Context
import com.github.dhaval2404.base.shared_pref.SharedPrefManager
import com.github.dhaval2404.callrecorder.setting.R
import org.koin.core.KoinComponent

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 26 Jan 2020
 */
class SettingPrefImpl(
    private val context: Context,
    private val manager: SharedPrefManager
) : SettingPref, KoinComponent {

    override fun isDarkTheme(): Boolean {
        return manager.getBoolean(context.getString(R.string.key_dark_theme))
    }

    override fun isRecordingEnabled(): Boolean {
        return manager.get(context.getString(R.string.key_call_recording), true)
    }
}
