package com.github.dhaval2404.callrecorder.home.screen.inbox

import android.app.Application
import android.view.MenuItem
import androidx.databinding.Bindable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.github.dhaval2404.base.BaseViewModel
import com.github.dhaval2404.callrecorder.callrecord.data.model.Recording
import com.github.dhaval2404.callrecorder.callrecord.data.repo.CallRecordingRepo
import com.github.dhaval2404.callrecorder.home.R
import com.github.dhaval2404.callrecorder.home.screen.search.SearchRecordingAdapter
import com.orhanobut.logger.Logger
import java.io.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Jan 2020
 */
class InboxViewModel(application: Application) : BaseViewModel<InboxNavigator>(application),
    KoinComponent {

    private val mCallRecordingRepo by inject<CallRecordingRepo>()
    private var searchQuery = ""

    private val mSearchRecordingAdapter by lazy {
        SearchRecordingAdapter(this)
    }

    private val mInboxAdapter by lazy {
        InboxAdapter(this)
    }

    @Bindable
    fun getSearchQuery(): String {
        return searchQuery
    }

    @Bindable
    fun setSearchQuery(query: String) {
        this.searchQuery = query
        mSearchRecordingAdapter.filter.filter(searchQuery)
    }

    fun getAdapter() = mInboxAdapter

    fun getSearchAdapter() = mSearchRecordingAdapter

    fun showAllRecordings(owner: LifecycleOwner, isSearchListing: Boolean = false) {
        CoroutineScope(Dispatchers.Main).launch {
            val list = withContext(Dispatchers.IO) {
                mCallRecordingRepo.getAll()
            }
            list.observe(owner, Observer<List<Recording>> {
                Logger.w("showAllRecordings: ${it.size}")
                if (isSearchListing) {
                    mSearchRecordingAdapter.refresh(it)
                } else {
                    mInboxAdapter.refresh(it)
                }
            })
        }
    }

    fun showFavouriteRecordings(owner: LifecycleOwner) {
        CoroutineScope(Dispatchers.Main).launch {
            val list = withContext(Dispatchers.IO) {
                mCallRecordingRepo.getFavourites()
            }
            list.observe(owner, Observer<List<Recording>> {
                mInboxAdapter.refresh(it)
                Logger.w("showFavouriteRecordings: ${it.size}")
            })
        }
    }

    fun playAudio(recording: Recording) {
        mNavigator?.playAudio(File(recording.audioPath))
    }

    fun handleMenuItemClick(menuItem: MenuItem, recording: Recording) {
        when (menuItem.itemId) {
            R.id.action_favourite -> {
                setFavourite(recording)
            }
            R.id.action_make_call -> {
                mNavigator?.makeCall(recording.phoneNumber)
            }
            R.id.action_send_sms -> {
                mNavigator?.sendSMS(recording.phoneNumber)
            }
            R.id.action_delete -> {
                mNavigator?.deleteRecording(recording)
            }
        }
    }

    private fun setFavourite(recording: Recording) {
        CoroutineScope(Dispatchers.Main).launch {
            val isFavourite = !recording.isFavourite
            withContext(Dispatchers.IO) {
                mCallRecordingRepo.setFavourite(recording.id, isFavourite)
            }
            if (isFavourite) {
                mNavigator?.showMessage(R.string.message_favourite_marked)
            } else {
                mNavigator?.showMessage(R.string.message_favourite_removed)
            }
        }
    }

    fun deleteRecording(recording: Recording) {
        CoroutineScope(Dispatchers.Main).launch {
            withContext(Dispatchers.IO) {
                mCallRecordingRepo.deleteById(recording.id)
            }
            mNavigator?.showMessage(R.string.message_recording_removed)
        }
    }
}
