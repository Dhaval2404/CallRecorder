package com.github.dhaval2404.callrecorder.callrecord.data.repo

import com.github.dhaval2404.callrecorder.callrecord.data.dao.ContactDao
import com.github.dhaval2404.callrecorder.callrecord.data.entity.Contact

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 25 Jan 2020
 */
class ContactRepoImpl(private val contactDao: ContactDao) : ContactRepo {

    override fun insert(contacts: List<Contact>) = contactDao.insert(contacts)

    override fun clear() = contactDao.clear()
}
