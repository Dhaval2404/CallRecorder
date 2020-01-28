package com.github.dhaval2404.callrecorder.permission.permission_request

import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Jan 2020
 */
object PermissionBinding {

    @JvmStatic
    @BindingAdapter("imgSrc")
    fun setImageResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }
}
