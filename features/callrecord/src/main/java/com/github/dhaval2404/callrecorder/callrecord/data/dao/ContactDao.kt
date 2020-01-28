package com.github.dhaval2404.callrecorder.callrecord.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.dhaval2404.callrecorder.callrecord.data.entity.Contact

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 25 Jan 2020
 */
@Dao
interface ContactDao {

    @Insert
    fun insert(contacts: List<Contact>)

    @Query("DELETE FROM contact")
    fun clear()
}
