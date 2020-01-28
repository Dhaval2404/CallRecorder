package com.github.dhaval2404.callrecorder.permission.permission_request

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.android.parcel.Parcelize

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Jan 2020
 */
@Parcelize
data class Permission(
    @StringRes
    val title: Int,
    @StringRes
    val description: Int,
    val permission: String,
    val icon: Int,
    val isRolePermission: Boolean = false,
    val isMandatory: Boolean = true
) : Parcelable
