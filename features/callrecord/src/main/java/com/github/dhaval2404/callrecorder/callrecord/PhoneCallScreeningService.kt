package com.github.dhaval2404.callrecorder.callrecord

import android.telecom.Call
import android.telecom.CallScreeningService
import com.orhanobut.logger.Logger

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 25 Jan 2020
 */
class PhoneCallScreeningService : CallScreeningService() {

    override fun onScreenCall(callDetails: Call.Details) {
        PhoneCallReceiver.setPhoneNumber(callDetails.handle.schemeSpecificPart)
        Logger.w("Caller:" + callDetails.handle.schemeSpecificPart.toString())
        respondToCall(callDetails, CallResponse.Builder().build())
    }
}
