package com.github.dhaval2404.callrecorder.callrecord.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaRecorder
import android.os.IBinder
import android.telephony.PhoneNumberUtils
import com.github.dhaval2404.base.action.Actions
import com.github.dhaval2404.callrecorder.callrecord.CallType
import com.github.dhaval2404.callrecorder.callrecord.data.entity.CallRecording
import com.github.dhaval2404.callrecorder.callrecord.data.repo.CallRecordingRepo
import com.github.dhaval2404.callrecorder.callrecord.util.FileHelper
import com.github.dhaval2404.callrecorder.callrecord.util.NotificationManager
import com.orhanobut.logger.Logger
import java.io.File
import java.io.IOException
import java.util.Locale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

/**
 * Created by dhaval on 7/9/17.
 */
class PhoneCallRecordService : Service(), MediaRecorder.OnInfoListener,
    MediaRecorder.OnErrorListener {

    private var isRecording = false

    private var mRecordingStartTime: Long = 0
    private var mMediaRecorder: MediaRecorder? = null

    private var mRecordingFile: File? = null
    private var mCallType: CallType? = null
    private var mPhoneNumber: String? = null

    private val mCallRecordingRepo by inject<CallRecordingRepo>()

    override fun onCreate() {
        super.onCreate()
        mMediaRecorder = MediaRecorder()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val action = intent.action
        if (action == null) {
            Logger.e("Record action not available. Ignoring onStartCommand call")
        } else {
            Logger.e("Action : $action")
            when (action) {
                START_RECORDING -> startRecording(intent)
                STOP_RECORDING -> stopRecording()
                CANCEL_RECORDING -> cancelRecording()
            }
        }
        return START_NOT_STICKY
    }

    private fun startRecording(intent: Intent) {
        if (isRecording) {
            Logger.e("Record service already running")
            return
        }
        mCallType = intent.getSerializableExtra(EXTRA_CALL_TYPE) as CallType
        mPhoneNumber = intent.getStringExtra(EXTRA_PHONE_NUMBER)

        val audioSource = MediaRecorder.AudioSource.VOICE_COMMUNICATION
        val audioFormat = MediaRecorder.OutputFormat.AMR_NB
        val audioEncoder = MediaRecorder.AudioEncoder.AMR_NB

        mRecordingFile = FileHelper(this).getAudioFile()

        mMediaRecorder?.reset()
        mMediaRecorder?.setAudioSource(audioSource)
        mMediaRecorder?.setOutputFormat(audioFormat)
        mMediaRecorder?.setAudioEncoder(audioEncoder)
        mMediaRecorder?.setOutputFile(mRecordingFile?.absolutePath)
        mMediaRecorder?.setOnInfoListener(this)
        mMediaRecorder?.setOnErrorListener(this)

        try {
            mMediaRecorder?.prepare()
        } catch (e: IOException) {
            Logger.e("RecordService::onStart() IOException attempting recorder.prepare()")
            mMediaRecorder = null
            return
        }
        mMediaRecorder?.start()
        mRecordingStartTime = System.currentTimeMillis()
        isRecording = true

        val title = PhoneNumberUtils.formatNumber(mPhoneNumber, Locale.getDefault().country)
        var message = "Recording Incoming Call"
        if (mCallType === CallType.OUTGOING_CALL) {
            message = "Recording Outgoing Call"
        }
        val notificationIntent = Actions.getHomeAction(this)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val notification = NotificationManager(this)
            .getNotification(title, message)
            .setTicker("Recording Started")
            .setContentIntent(pendingIntent)
            .setAutoCancel(false)
            .build()

        startForeground(NOTIFICATION_ID, notification)
    }

    private fun cancelRecording() {
        stopMediaRecorder()
        if (mRecordingFile != null) {
            mRecordingFile?.delete()
        }
        stopService()
    }

    private fun stopService() {
        NotificationManager(this).cancel(NOTIFICATION_ID)
        isRecording = false
        stopForeground(true)
        stopSelf()
    }

    private fun stopMediaRecorder(): Boolean {
        if (mMediaRecorder != null) {
            mMediaRecorder?.stop()
            mMediaRecorder?.release()
            mMediaRecorder = null
            return true
        }
        return false
    }

    private fun stopRecording() {
        if (stopMediaRecorder() && isRecording) {

            val recording = CallRecording(
                phoneNumber = mPhoneNumber!!,
                callType = mCallType!!,
                audioPath = mRecordingFile!!.absolutePath,
                duration = System.currentTimeMillis() - mRecordingStartTime
            )

            Logger.w("Add Recording:$recording")

            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    mCallRecordingRepo.insert(recording)
                }
            }
            // update notification
/*
            val message = TelephonyManager.getContactDisplayName(this, mPhoneNumber);
            val title = "Recorded "+ (mCallType.equals(AppConstant.CallType.INCOMING_CALL)?"Incoming Call":"Outgoing Call");
            mNotificationHelper.showRecordingCompleteNotification(title, message, id);
*/
        }
        stopService()
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.i("onDestroy>> Release Media Recorder")
        stopRecording()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onInfo(mediaRecorder: MediaRecorder, what: Int, extra: Int) {
        when (what) {
            MediaRecorder.MEDIA_RECORDER_INFO_UNKNOWN ->
                Logger.e("MEDIA_RECORDER_INFO_UNKNOWN, Extra: $extra")
            MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED ->
                Logger.e("MEDIA_RECORDER_INFO_MAX_DURATION_REACHED, Extra: $extra")
            MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED ->
                Logger.e("MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED, Extra: $extra")
        }
        cancelRecording()
    }

    override fun onError(mediaRecorder: MediaRecorder, i: Int, i1: Int) {
        cancelRecording()
    }

    companion object {
        private const val NOTIFICATION_ID = 24
        const val RECORDING_INFO_NOTIFICATION_ID = 26

        const val START_RECORDING = "START_RECORDING"
        const val STOP_RECORDING = "STOP_RECORDING"
        const val CANCEL_RECORDING = "CANCEL_RECORDING"
        const val EXTRA_PHONE_NUMBER = "EXTRA_PHONE_NUMBER"
        const val EXTRA_CALL_TYPE = "EXTRA_CALL_TYPE"
        const val EXTRA_SUBSCRIPTION_ID = "EXTRA_SUBSCRIPTION_ID"
    }

    /*fun showRecordingCompleteNotification(
        title: String?,
        message: String?,
        recordId: Long
    ) {

        val ntIntent = Intent(this, NotificationActionReceiver::class.java)
        ntIntent.action = NotificationActionReceiver.ACTION_ADD_NOTE
        ntIntent.putExtra(NotificationActionReceiver.EXTRA_RECORD_ID, recordId)
        val ntPIntent = PendingIntent.getBroadcast(this, 0, ntIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val ntAction = NotificationCompat.Action("Add Notes", ntPIntent)

        val notificationIntent = Actions.getHomeAction(this)
        //notificationIntent.putExtra(CallDetailActivity.EXTRA_RECORD_ID, recordId)
        //notificationIntent.putExtra(CallDetailActivity.EXTRA_START_FROM_NOTIFICATION, true)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, recordId.toInt(), notificationIntent, 0)
        val notification: Notification = NotificationManager(this).getNotification(title, message)
                .setTicker("Recording Finished")
                .setContentIntent(pendingIntent)
                .addAction(ntAction)
                .setOngoing(false)
                .build()

        NotificationManager(this).notify(RECORDING_INFO_NOTIFICATION_ID, notification)
    }*/
}
