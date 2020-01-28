package com.github.dhaval2404.callrecorder.callrecord.util

import android.content.Context
import android.provider.ContactsContract
import com.github.dhaval2404.callrecorder.callrecord.data.entity.Contact
import com.orhanobut.logger.Logger

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 25 Jan 2020
 */
object ContactManager {

    private const val _ID = ContactsContract.Contacts._ID
    private const val CONTACT_COLUMN_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
    private const val CONTACT_COLUMN_DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME
    private const val CONTACT_COLUMN_PHOTO = ContactsContract.CommonDataKinds.Phone.PHOTO_URI
    private const val CONTACT_COLUMN_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER
    private const val CONTACT_COLUMN_NUMBER_TYPE = ContactsContract.CommonDataKinds.Phone.TYPE

    fun readContact(context: Context): List<Contact> {
        val contentResolver = context.contentResolver

        val contactList = ArrayList<Contact>()

        val mainProjection = arrayOf(_ID, CONTACT_COLUMN_DISPLAY_NAME, CONTACT_COLUMN_PHOTO)
        val phoneProjection = arrayOf(CONTACT_COLUMN_NUMBER, CONTACT_COLUMN_NUMBER_TYPE)

        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            mainProjection, null, null, null
        )

        // Loop for every contact in the phone
        var idColumnIndex = -1
        var nameColumnIndex = -1
        var photoColumnIndex = -1
        cursor?.moveToFirst()
        while (cursor != null && !cursor.isAfterLast) {
            if (idColumnIndex == -1) {
                idColumnIndex = cursor.getColumnIndex(_ID)
            }
            if (nameColumnIndex == -1) {
                nameColumnIndex = cursor.getColumnIndex(CONTACT_COLUMN_DISPLAY_NAME)
            }
            if (photoColumnIndex == -1) {
                photoColumnIndex = cursor.getColumnIndex(CONTACT_COLUMN_PHOTO)
            }

            val id = cursor.getLong(idColumnIndex)
            val name = cursor.getString(nameColumnIndex)
            val imagePath = cursor.getString(photoColumnIndex)

            Logger.w(name)

            // Query and loop for every phone number of the contact
            val phoneCursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, phoneProjection,
                "$CONTACT_COLUMN_ID = ?", arrayOf(id.toString()), null
            )
            Logger.w(phoneCursor!!.count.toString())

            val list = generateSequence { if (phoneCursor.moveToNext()) phoneCursor else null }
                .map {
                    Contact(
                        name = name,
                        imagePath = imagePath,
                        phoneNumber = getStrippedPhoneNumber(it.getString(0)),
                        phoneType = getPhoneType(it.getInt(1))
                    )
                }.filter { !it.phoneNumber.trim().isBlank() }
                .toList()

            contactList.addAll(list)

            cursor.moveToNext()
        }

        cursor?.close()

        return contactList
    }

    private fun getStrippedPhoneNumber(phoneNumber: String): String {
        return phoneNumber.replace("[^0-9+]".toRegex(), "")
    }

    private fun getPhoneType(type: Int): String {
        return when (type) {
            ContactsContract.CommonDataKinds.Phone.TYPE_HOME -> "Home"
            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE -> "Mobile"
            ContactsContract.CommonDataKinds.Phone.TYPE_WORK -> "Work"
            else -> "Mobile"
        }
    }
}
