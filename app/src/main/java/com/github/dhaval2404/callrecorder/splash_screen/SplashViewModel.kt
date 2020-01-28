package com.github.dhaval2404.callrecorder.splash_screen

import android.app.Application
import com.github.dhaval2404.base.BaseViewModel
import com.github.dhaval2404.base.action.Actions
import com.github.dhaval2404.base.util.Run
import com.github.dhaval2404.callrecorder.permission.util.PermissionManager
import org.koin.core.KoinComponent

/**
 * ViewModel for SplashActivity
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 22 January 2020
 */
class SplashViewModel(application: Application) : BaseViewModel<SplashNavigator>(application),
    KoinComponent {

    private val mSplashDelay = 3000L

    fun preProcessing() {
        Run.after(mSplashDelay, ::startNextActivity)
    }

    private fun startNextActivity() {
        val intent = if (!PermissionManager.isMandatoryPermissionGranted(mContext)) {
            Actions.getPermissionIntent(mContext)
        } else {
            Actions.getHomeAction(mContext)
        }
        mNavigator?.startNextActivity(intent)
    }
}
