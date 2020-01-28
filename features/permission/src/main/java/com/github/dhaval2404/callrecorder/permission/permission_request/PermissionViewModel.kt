package com.github.dhaval2404.callrecorder.permission.permission_request

import android.app.Application
import com.github.dhaval2404.base.BaseViewModel
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Jan 2020
 */
class PermissionViewModel(application: Application) :
    BaseViewModel<PermissionNavigator>(application) {

    fun skipPermission() {
        mNavigator?.showNextPermission()
    }

    fun checkPermission(permission: Permission) {
        if (permission.isRolePermission) {
            mNavigator?.requestRoleManager()
        } else {
            requestPermission(permission)
        }
    }

    private fun requestPermission(permission: Permission) {
        TedPermission.with(mContext)
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    mNavigator?.showNextPermission()
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                }
            })
            .setPermissions(permission.permission)
            .check()
    }
}
