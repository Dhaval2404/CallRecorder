package com.github.dhaval2404.base.util

import android.os.Build

/**
 * Android SDK Util
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 27 Jan 2020
 */
object AndroidSDK {

    fun isAndroidP() = Build.VERSION.SDK_INT == Build.VERSION_CODES.P

    fun isAndroidQAndAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
}
