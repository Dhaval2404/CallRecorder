package com.github.dhaval2404.callrecorder.callrecord.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 25 Jan 2020
 */
@Entity(tableName = "contact")
data class Contact(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "image_path")
    val imagePath: String?,

    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,

    @ColumnInfo(name = "phoneType")
    val phoneType: String
)
