package com.github.dhaval2404.callrecorder.home.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 27 Jan 2020
 */
object IntentUtil {

    fun playAudio(context: Context, file: File) {
        val uri = FileProvider.getUriForFile(context, context.packageName + ".provider", file)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(intent)
    }

    fun dialCall(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        context.startActivity(intent)
    }

    fun sendSMS(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("smsto:$phoneNumber")
        context.startActivity(intent)
    }
}
