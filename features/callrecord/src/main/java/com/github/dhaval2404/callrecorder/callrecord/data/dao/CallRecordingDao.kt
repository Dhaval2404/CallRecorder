package com.github.dhaval2404.callrecorder.callrecord.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.github.dhaval2404.callrecorder.callrecord.data.entity.CallRecording
import com.github.dhaval2404.callrecorder.callrecord.data.model.Recording

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 25 Jan 2020
 */
@Dao
interface CallRecordingDao {

    @Insert
    fun insert(recording: CallRecording): Long

    @Update
    fun update(recording: CallRecording): Int

    @Query("UPDATE call_recording SET is_favourite=:isFavourite where id=:id")
    fun setFavourite(id: Long, isFavourite: Boolean): Int

    @Query("SELECT rec.*, contact.name, contact.image_path, contact.phoneType FROM call_recording as rec LEFT JOIN contact ON substr(rec.phone_number,-9) == substr(contact.phone_number,-9) WHERE rec.id=:id GROUP BY rec.id ORDER BY created_at DESC")
    fun getById(id: Long): LiveData<Recording>

    @Query("SELECT rec.*, contact.name, contact.image_path, contact.phoneType FROM call_recording as rec LEFT JOIN contact ON substr(rec.phone_number,-9) == substr(contact.phone_number,-9) GROUP BY rec.id ORDER BY created_at DESC")
    fun getAll(): LiveData<List<Recording>>

    @Query("SELECT rec.*, contact.name, contact.image_path, contact.phoneType FROM call_recording as rec LEFT JOIN contact ON substr(rec.phone_number,-9) == substr(contact.phone_number,-9) WHERE rec.is_favourite = 1 GROUP BY rec.id ORDER BY created_at DESC")
    fun getFavourites(): LiveData<List<Recording>>

    @Query("DELETE FROM call_recording WHERE id = :recordingId")
    fun deleteById(recordingId: Long): Int

    @Query("DELETE FROM call_recording")
    fun clear()
}
