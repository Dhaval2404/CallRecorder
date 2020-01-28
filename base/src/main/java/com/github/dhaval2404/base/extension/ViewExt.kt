package com.github.dhaval2404.base.extension

import android.view.View

fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
