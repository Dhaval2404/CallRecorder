package com.github.dhaval2404.callrecorder.callrecord.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.dhaval2404.callrecorder.callrecord.CallType
import java.util.Calendar
import kotlinx.android.parcel.Parcelize

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 25 Jan 2020
 */
@Parcelize
@Entity(tableName = "call_recording")
data class CallRecording(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,

    @ColumnInfo(name = "call_type")
    val callType: CallType,

    @ColumnInfo(name = "audio_path")
    val audioPath: String,

    @ColumnInfo(name = "duration")
    val duration: Long,

    @ColumnInfo(name = "note")
    val note: String? = null,

    @ColumnInfo(name = "is_favourite")
    val isFavourite: Boolean = false,

    @ColumnInfo(name = "created_at")
    val createdAt: Calendar = Calendar.getInstance()

) : Parcelable
