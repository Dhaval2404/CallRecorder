package com.github.dhaval2404.callrecorder.callrecord.util

import android.content.Context
import android.content.ContextWrapper
import android.os.Environment
import android.widget.Toast
import com.orhanobut.logger.Logger
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Show Notification
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 05 April 2018
 */
class FileHelper(context: Context?) : ContextWrapper(context) {

    companion object {
        private val TAG = FileHelper::class.java.simpleName

        private var FILE_LOCATION = File(Environment.getExternalStorageDirectory(), "CallRecorder")
    }

    fun getAudioFile(): File? {
        val sdf1 = SimpleDateFormat("MMM yyyy", Locale.ENGLISH)
        val dir = File(FILE_LOCATION, sdf1.format(Date()))

        if (!dir.exists()) {
            try {
                dir.mkdirs()
            } catch (e: Exception) {
                showError("CallRecorder was unable to create the directory $dir to store recordings: $e")
                return null
            }
        } else {
            if (!dir.canWrite()) {
                showError("CallRecorder does not have write permission for the directory directory $dir to store recordings")
                return null
            }
        }

        // String prefix = "call";
        val sdf = SimpleDateFormat("yyyyMMdd-HHmm", Locale.ENGLISH)
        val prefix = "REC-" + sdf.format(Date())
        val suffix = ".amr"

        return try {
            val file = File(dir, prefix + suffix)
            file.createNewFile()
            file
        } catch (e: IOException) {
            showError("CallRecorder was unable to create temp file in $dir: $e")
            null
        }
    }

    private fun showError(message: String) {
        Logger.e(TAG, message)
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
}
