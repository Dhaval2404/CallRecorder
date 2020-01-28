package com.github.dhaval2404.callrecorder.permission.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.role.RoleManager
import android.content.Context
import android.content.pm.PackageManager
import com.github.dhaval2404.base.util.AndroidSDK
import com.github.dhaval2404.callrecorder.permission.R
import com.github.dhaval2404.callrecorder.permission.permission_request.Permission

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 25 Jan 2020
 */
object PermissionManager {

    const val ROLE_CALL_SCREENING = "android.app.role.CALL_SCREENING"

    @SuppressLint("WrongConstant", "NewApi")
    fun getRoleManager(context: Context): RoleManager {
        return context.getSystemService(Context.ROLE_SERVICE) as RoleManager
    }

    @SuppressLint("NewApi")
    fun isMandatoryPermissionGranted(context: Context): Boolean {
        getPermissionList()
            .filter { it.isMandatory }
            .forEach { permission ->
                if (permission.isRolePermission) {
                    if (!getRoleManager(context).isRoleHeld(ROLE_CALL_SCREENING)) {
                        return false
                    }
                } else if (!hasPermission(context, permission.permission)) {
                    return false
                }
            }
        return true
    }

    fun hasPermission(context: Context, permission: String): Boolean {
        val result = context.checkCallingOrSelfPermission(permission)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun getPermissionList(): List<Permission> {
        val permissionList = ArrayList<Permission>()

        permissionList.add(
            Permission(
                title = R.string.title_permission_phone,
                description = R.string.message_permission_phone,
                permission = Manifest.permission.READ_PHONE_STATE,
                icon = R.drawable.outline_call_24
            )
        )

        if (AndroidSDK.isAndroidQAndAbove()) {
            permissionList.add(
                Permission(
                    title = R.string.title_permission_call_screening,
                    description = R.string.message_permission_call_screening,
                    permission = "",
                    icon = R.drawable.outline_call_24,
                    isRolePermission = true
                )
            )
        } else {
            permissionList.add(
                Permission(
                    title = R.string.title_permission_call_log,
                    description = R.string.message_permission_call_log,
                    permission = Manifest.permission.READ_CALL_LOG,
                    icon = R.drawable.outline_call_24
                )
            )
        }

        permissionList.add(
            Permission(
                title = R.string.title_permission_record_audio,
                description = R.string.message_permission_record_audio,
                permission = Manifest.permission.RECORD_AUDIO,
                icon = R.drawable.outline_mic_none_24
            )
        )

        permissionList.add(
            Permission(
                title = R.string.title_permission_external_storage,
                description = R.string.message_permission_external_storage,
                permission = Manifest.permission.WRITE_EXTERNAL_STORAGE,
                icon = R.drawable.outline_sd_storage_24
            )
        )

        permissionList.add(
            Permission(
                title = R.string.title_permission_contacts,
                description = R.string.message_permission_contact,
                permission = Manifest.permission.READ_CONTACTS,
                icon = R.drawable.outline_person_24,
                isMandatory = false
            )
        )

        return permissionList
    }
}
