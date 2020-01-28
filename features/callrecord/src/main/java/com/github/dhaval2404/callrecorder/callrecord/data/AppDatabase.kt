package com.github.dhaval2404.callrecorder.callrecord.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.dhaval2404.callrecorder.callrecord.data.converters.CallTypeConverter
import com.github.dhaval2404.callrecorder.callrecord.data.converters.TimestampConverter
import com.github.dhaval2404.callrecorder.callrecord.data.dao.CallRecordingDao
import com.github.dhaval2404.callrecorder.callrecord.data.dao.ContactDao
import com.github.dhaval2404.callrecorder.callrecord.data.entity.CallRecording
import com.github.dhaval2404.callrecorder.callrecord.data.entity.Contact

/**
 * TODO: Add Class Header
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 18 Dec 2019
 */
@Database(entities = [CallRecording::class, Contact::class], version = 1)
@TypeConverters(value = [TimestampConverter::class, CallTypeConverter::class])
abstract class AppDatabase : RoomDatabase() {

    companion object {

        fun getInstance(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "CallRecording"
            ).build()
    }

    abstract fun callRecordingDao(): CallRecordingDao

    abstract fun contactDao(): ContactDao
}
