package com.github.dhaval2404.callrecorder.callrecord.data.repo

import com.github.dhaval2404.callrecorder.callrecord.data.entity.Contact

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 25 Jan 2020
 */
interface ContactRepo {

    fun insert(contacts: List<Contact>)

    fun clear()
}
