package com.github.dhaval2404.callrecorder.callrecord

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.github.dhaval2404.callrecorder.callrecord.service.PhoneCallRecordService
import com.github.dhaval2404.callrecorder.setting.data.SettingPref
import com.orhanobut.logger.Logger
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 25 Jan 2020
 */
class PhoneCallStateReceiver : PhoneCallReceiver(), KoinComponent {

    private val mSettingPref: SettingPref by inject()

    override fun onCallStarted(context: Context, callType: CallType, phoneNumber: String?) {
        Logger.w("callStarted: ${callType.name}, $phoneNumber")
        startCallRecordingService(context, callType, phoneNumber)
    }

    override fun onCallOffHook(context: Context, callType: CallType, phoneNumber: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCallEnded(context: Context, callType: CallType, phoneNumber: String?) {
        Logger.w("onCallEnded: ${callType.name}, $phoneNumber")
        stopRecordingService(context, phoneNumber)
    }

    private fun startCallRecordingService(
        context: Context,
        callType: CallType,
        phoneNumber: String?
    ) {
        if (!mSettingPref.isRecordingEnabled()) {
            Logger.w("Recording not enabled")
            return
        }
        val startIntent = Intent(context, PhoneCallRecordService::class.java)
        startIntent.action = PhoneCallRecordService.START_RECORDING
        startIntent.putExtra(PhoneCallRecordService.EXTRA_CALL_TYPE, callType)
        startIntent.putExtra(PhoneCallRecordService.EXTRA_SUBSCRIPTION_ID, 1)
        startIntent.putExtra(PhoneCallRecordService.EXTRA_PHONE_NUMBER, phoneNumber ?: "")
        ContextCompat.startForegroundService(context, startIntent)
    }

    private fun stopRecordingService(context: Context, phoneNumber: String?) {
        if (!mSettingPref.isRecordingEnabled()) {
            Logger.w("Recording not enabled")
            return
        }
        val stopIntent = Intent(context, PhoneCallRecordService::class.java)
        stopIntent.action = PhoneCallRecordService.STOP_RECORDING
        stopIntent.putExtra(PhoneCallRecordService.EXTRA_SUBSCRIPTION_ID, 1)
        stopIntent.putExtra(PhoneCallRecordService.EXTRA_PHONE_NUMBER, phoneNumber ?: "")
        context.stopService(stopIntent)
    }
}
