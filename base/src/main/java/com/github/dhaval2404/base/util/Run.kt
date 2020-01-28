package com.github.dhaval2404.base.util

import android.os.Handler

class Run {

    companion object {

        fun after(delay: Long, func: () -> Unit) {
            Handler().postDelayed(func, delay)
        }
    }
}
