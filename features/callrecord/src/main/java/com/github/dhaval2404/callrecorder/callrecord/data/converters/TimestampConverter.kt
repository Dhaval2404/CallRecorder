package com.github.dhaval2404.callrecorder.callrecord.data.converters

import androidx.room.TypeConverter
import java.util.Calendar

class TimestampConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Calendar? {
        return value?.let {
            Calendar.getInstance().apply {
                timeInMillis = value
            }
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: Calendar?): Long? {
        return date?.timeInMillis
    }
}
