package com.github.dhaval2404.callrecorder.callrecord.data.converters

import androidx.room.TypeConverter
import com.github.dhaval2404.callrecorder.callrecord.CallType

class CallTypeConverter {

    @TypeConverter
    fun stringToCallType(value: String): CallType {
        return CallType.values().first { it.name == value }
    }

    @TypeConverter
    fun callTypeToString(callType: CallType) = callType.name
}
