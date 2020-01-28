package com.github.dhaval2404.base.extension

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import com.github.dhaval2404.base.R

fun Activity.launchActivity(
    cls: Class<out Activity>,
    bundle: Bundle = Bundle(),
    clearStack: Boolean = false
) {
    val intent = Intent(this, cls)
    intent.putExtras(bundle)

    val animBundle = getAnimationBundle()

    startActivity(intent, animBundle)

    if (clearStack) {
        finishAffinity()
    }
}

fun Activity.launchActivityForResult(
    cls: Class<out Activity>,
    bundle: Bundle = Bundle(),
    reqCode: Int = 2404,
    clearStack: Boolean = false
) {
    val intent = Intent(this, cls)
    intent.putExtras(bundle)

    val animBundle = getAnimationBundle()

    startActivityForResult(intent, reqCode, animBundle)

    if (clearStack) {
        finishAffinity()
    }
}

fun Activity.launchActivity(
    intent: Intent,
    bundle: Bundle = Bundle(),
    clearStack: Boolean = false
) {
    intent.putExtras(bundle)

    val animBundle = getAnimationBundle()

    startActivity(intent, animBundle)

    if (clearStack) {
        finishAffinity()
    }
}

fun Activity.getAnimationBundle(): Bundle =
    ActivityOptions
        .makeCustomAnimation(
            this,
            R.anim.activity_slide_in_1,
            R.anim.activity_slide_in_2
        )
        .toBundle()

fun overridePendingTransition(activity: Activity) {
    activity.overridePendingTransition(
        R.anim.activity_slide_from_left,
        R.anim.activity_slide_to_right
    )
}
