package com.github.dhaval2404.callrecorder.home.util

import java.util.Calendar

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 25 Jan 2020
 */
object TimeUtil {
    fun isSameDay(calendar1: Calendar, calendar2: Calendar): Boolean {
        return calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
    }
}
