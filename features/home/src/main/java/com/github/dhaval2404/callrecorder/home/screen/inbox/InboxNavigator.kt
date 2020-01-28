package com.github.dhaval2404.callrecorder.home.screen.inbox

import com.github.dhaval2404.callrecorder.callrecord.data.model.Recording
import java.io.File

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Jan 2020
 */
interface InboxNavigator {

    fun makeCall(phoneNumber: String)

    fun sendSMS(phoneNumber: String)

    fun playAudio(file: File)

    fun showMessage(message: String)

    fun showMessage(messageRes: Int)

    fun deleteRecording(recording: Recording)
}
