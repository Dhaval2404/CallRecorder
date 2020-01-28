package com.github.dhaval2404.base.extension

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment

// Ref: https://proandroiddev.com/code-clean-up-with-kotlin-19ee1c8c0719

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T = this.apply {
    arguments = Bundle().apply(argsBuilder)
}

inline fun <reified T : Any> Fragment.argument(key: String, default: T? = null) = lazy {
    val value = arguments?.get(key)
    if (value is T) value else default
}

inline fun <reified T : Any> Activity.extra(key: String, default: T? = null) = lazy {
    val value = intent.extras?.get(key)
    if (value is T) value else default
}
