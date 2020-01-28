package com.github.dhaval2404.callrecorder.callrecord.service

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.JobIntentService
import com.github.dhaval2404.callrecorder.callrecord.data.repo.ContactRepo
import com.github.dhaval2404.callrecorder.callrecord.util.ContactManager
import org.koin.android.ext.android.inject

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 25 Jan 2020
 */
class ContactSyncService : JobIntentService() {

    companion object {
        private const val JOB_ID = 101

        fun startService(context: Context) {
            enqueueWork(context, ContactSyncService::class.java, JOB_ID, Intent())
        }
    }

    private val mContactRepo by inject<ContactRepo>()

    override fun onHandleWork(intent: Intent) {
        val result = checkCallingOrSelfPermission(Manifest.permission.READ_CONTACTS)
        if (result == PackageManager.PERMISSION_GRANTED) {
            // Sync contact if permission granted
            val contacts = ContactManager.readContact(this)
            mContactRepo.clear()
            mContactRepo.insert(contacts)
        }
    }
}
