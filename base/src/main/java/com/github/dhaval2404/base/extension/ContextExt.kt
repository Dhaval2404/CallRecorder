package com.github.dhaval2404.base.extension

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

val Context.inflater: LayoutInflater get() = LayoutInflater.from(this)

fun Context.getColorRes(color: Int) = ContextCompat.getColor(this, color)

fun View.getColorRes(color: Int) = ContextCompat.getColor(context, color)

fun Context.getColorStateListRes(color: Int) = ColorStateList.valueOf(getColorRes(color))

fun View.getColorStateListRes(color: Int) = ColorStateList.valueOf(getColorRes(color))

fun Context.isLandscape(): Boolean {
    return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}

fun Context.setTheme(darkTheme: Boolean) {
    val theme = if (darkTheme) {
        AppCompatDelegate.MODE_NIGHT_YES
    } else {
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }
    AppCompatDelegate.setDefaultNightMode(theme)
}
