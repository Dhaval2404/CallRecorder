package com.github.dhaval2404.callrecorder.callrecord.data.repo

import com.github.dhaval2404.callrecorder.callrecord.data.dao.CallRecordingDao
import com.github.dhaval2404.callrecorder.callrecord.data.entity.CallRecording

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 25 Jan 2020
 */
class CallRecordingRepoImpl(private val callRecordingDao: CallRecordingDao) : CallRecordingRepo {

    override fun insert(recording: CallRecording) = callRecordingDao.insert(recording)

    override fun update(recording: CallRecording) = callRecordingDao.update(recording)

    override fun setFavourite(id: Long, isFavourite: Boolean) =
        callRecordingDao.setFavourite(id, isFavourite)

    override fun getById(id: Long) = callRecordingDao.getById(id)

    override fun getAll() = callRecordingDao.getAll()

    override fun getFavourites() = callRecordingDao.getFavourites()

    override fun deleteById(recordingId: Long) = callRecordingDao.deleteById(recordingId)

    override fun clear() = callRecordingDao.clear()
}
