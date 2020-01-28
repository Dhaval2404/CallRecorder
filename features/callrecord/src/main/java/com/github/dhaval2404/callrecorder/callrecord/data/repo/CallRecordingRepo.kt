package com.github.dhaval2404.callrecorder.callrecord.data.repo

import androidx.lifecycle.LiveData
import com.github.dhaval2404.callrecorder.callrecord.data.entity.CallRecording
import com.github.dhaval2404.callrecorder.callrecord.data.model.Recording

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 25 Jan 2020
 */
interface CallRecordingRepo {

    fun insert(recording: CallRecording): Long

    fun update(recording: CallRecording): Int

    fun setFavourite(id: Long, isFavourite: Boolean): Int

    fun getById(id: Long): LiveData<Recording>

    fun getAll(): LiveData<List<Recording>>

    fun getFavourites(): LiveData<List<Recording>>

    fun deleteById(recordingId: Long): Int

    fun clear()
}
