package com.github.dhaval2404.callrecorder.splash_screen

import android.content.Intent
import android.os.Bundle
import com.github.dhaval2404.base.BaseActivity
import com.github.dhaval2404.base.extension.launchActivity
import com.github.dhaval2404.callrecorder.R
import com.github.dhaval2404.callrecorder.databinding.ActivitySplashBinding
import org.koin.android.ext.android.get

/**
 * Splash Screen
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 22 January 2020
 */
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(), SplashNavigator {

    override fun getLayout() = R.layout.activity_splash

    override fun getViewModel(): SplashViewModel = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.setNavigator(this)
        mViewModel.preProcessing()
    }

    override fun startNextActivity(intent: Intent) {
        launchActivity(intent, clearStack = true)
    }
}
