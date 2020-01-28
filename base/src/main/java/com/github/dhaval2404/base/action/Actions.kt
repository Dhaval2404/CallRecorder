package com.github.dhaval2404.base.action

import android.content.Context
import android.content.Intent

/**
 * Manage Action for MVI
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 20 August 2019
 */
object Actions {

    private const val PERMISSION_ACTION = "com.github.dhaval2404.callrecorder.permission"
    private const val HOME_ACTION = "com.github.dhaval2404.callrecorder.home"

    fun getPermissionIntent(context: Context) = getIntent(context, PERMISSION_ACTION)

    fun getHomeAction(context: Context) = getIntent(context, HOME_ACTION)

    private fun getIntent(context: Context, action: String) = Intent(action)
        .setPackage(context.packageName)
}
