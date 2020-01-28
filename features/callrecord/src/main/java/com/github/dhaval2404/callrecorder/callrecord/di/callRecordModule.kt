package com.github.dhaval2404.callrecorder.callrecord.di

import com.github.dhaval2404.callrecorder.callrecord.data.AppDatabase
import com.github.dhaval2404.callrecorder.callrecord.data.repo.CallRecordingRepo
import com.github.dhaval2404.callrecorder.callrecord.data.repo.CallRecordingRepoImpl
import com.github.dhaval2404.callrecorder.callrecord.data.repo.ContactRepo
import com.github.dhaval2404.callrecorder.callrecord.data.repo.ContactRepoImpl
import org.koin.dsl.module

val callRecordModule = module {

    single { AppDatabase.getInstance(get()) }

    single { get<AppDatabase>().callRecordingDao() }

    single { get<AppDatabase>().contactDao() }

    single<CallRecordingRepo> { CallRecordingRepoImpl(get()) }

    single<ContactRepo> { ContactRepoImpl(get()) }
}
