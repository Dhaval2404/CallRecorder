package com.github.dhaval2404.callrecorder.callrecord

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager

abstract class PhoneCallReceiver : BroadcastReceiver() {

    companion object {
        private var isIncomingCall = false

        // The receiver will be recreated whenever android feels like it.  We need a static variable to remember data between instantiations
        private var lastCallState = TelephonyManager.CALL_STATE_IDLE

        // Because the passed incoming is only valid in ringing
        private var phoneNumber: String? = null

        fun setPhoneNumber(phoneNumber: String?) {
            if (!phoneNumber.isNullOrBlank()) {
                this.phoneNumber = phoneNumber
            }
        }
    }

    abstract fun onCallStarted(context: Context, callType: CallType, phoneNumber: String?)

    abstract fun onCallOffHook(context: Context, callType: CallType, phoneNumber: String?)

    abstract fun onCallEnded(context: Context, callType: CallType, phoneNumber: String?)

    private fun onCallStarted(context: Context, callType: CallType) {
        onCallStarted(context, callType, phoneNumber)
    }

    private fun onCallEnded(context: Context, callType: CallType) {
        onCallEnded(context, callType, phoneNumber)
    }

    override fun onReceive(context: Context, intent: Intent) {
        // We listen to two intents.  The new outgoing call only tells us of an outgoing call.  We use it to get the number.
        if (intent.action == Intent.ACTION_NEW_OUTGOING_CALL) {
            val phoneNumber = intent.extras?.getString(Intent.EXTRA_PHONE_NUMBER)
            setPhoneNumber(phoneNumber)
        } else {
            val incomingNumber = intent.extras?.getString(TelephonyManager.EXTRA_INCOMING_NUMBER)
            setPhoneNumber(incomingNumber)

            val stateExtra = intent.extras?.getString(TelephonyManager.EXTRA_STATE)
            val callState = when (stateExtra) {
                TelephonyManager.EXTRA_STATE_IDLE -> {
                    TelephonyManager.CALL_STATE_IDLE
                }
                TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                    TelephonyManager.CALL_STATE_OFFHOOK
                }
                TelephonyManager.EXTRA_STATE_RINGING -> {
                    TelephonyManager.CALL_STATE_RINGING
                }
                else -> {
                    0
                }
            }

            onCallStateChanged(context, callState)
        }
    }

    /**
     * Deals with actual events
     * Incoming call-  goes from IDLE to RINGING when it rings, to OFFHOOK when it's answered, to IDLE when its hung up
     * Outgoing call-  goes from IDLE to OFFHOOK when it dials out, to IDLE when hung up
     */
    private fun onCallStateChanged(context: Context, state: Int) {
        if (lastCallState == state) {
            // No change, debounce extras
            return
        }
        when (state) {
            TelephonyManager.CALL_STATE_RINGING -> {
                isIncomingCall = true
                onCallStarted(context, CallType.INCOMING_CALL)
            }
            TelephonyManager.CALL_STATE_OFFHOOK ->
                // Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                if (lastCallState != TelephonyManager.CALL_STATE_RINGING) {
                    isIncomingCall = false
                    onCallStarted(context, CallType.OUTGOING_CALL)
                }
            TelephonyManager.CALL_STATE_IDLE ->
                // Went to idle-  this is the end of a call.  What type depends on previous state(s)
                if (lastCallState == TelephonyManager.CALL_STATE_RINGING) {
                    // Ring but no pickup-  a miss
                    onCallEnded(context, CallType.MISSED_CALL)
                } else if (isIncomingCall) {
                    onCallEnded(context, CallType.INCOMING_CALL)
                } else {
                    onCallEnded(context, CallType.OUTGOING_CALL)
                }
        }
        lastCallState = state
    }
}
