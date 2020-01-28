package com.github.dhaval2404.callrecorder.splash_screen

import android.content.Intent

/**
 * Contract Between SplashActivity and SplashViewModel
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 22 January 2020
 */
interface SplashNavigator {

    fun startNextActivity(intent: Intent)
}
