package com.github.dhaval2404.callrecorder.home.screen.inbox

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.github.dhaval2404.base.BaseFragment
import com.github.dhaval2404.callrecorder.callrecord.data.model.Recording
import com.github.dhaval2404.callrecorder.home.R
import com.github.dhaval2404.callrecorder.home.databinding.FragmentInboxBinding
import com.github.dhaval2404.callrecorder.home.screen.search.SearchRecordingFragment
import com.github.dhaval2404.callrecorder.home.util.IntentUtil
import com.google.android.material.snackbar.Snackbar
import java.io.File
import kotlinx.android.synthetic.main.content_recording_listing.*
import kotlinx.android.synthetic.main.content_search_toolbar.*
import org.koin.android.ext.android.get

/**
 * InboxFragment
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Jan 2020
 */
open class BaseInboxFragment : BaseFragment<FragmentInboxBinding, InboxViewModel>(),
    InboxNavigator {

    override fun getLayout() = R.layout.fragment_inbox

    override fun getViewModel(): InboxViewModel = get()

    override fun onCreateView() {
        super.onCreateView()
        mViewModel.setNavigator(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recordingRV.setEmptyView(view.findViewById(R.id.emptyView))
        recordingRV.adapter = mViewModel.getAdapter()

        searchContactCard.setOnClickListener {
            SearchRecordingFragment.getInstance()
                .show(childFragmentManager)
        }
    }

    override fun makeCall(phoneNumber: String) {
        IntentUtil.dialCall(mActivity, phoneNumber)
    }

    override fun sendSMS(phoneNumber: String) {
        IntentUtil.sendSMS(mActivity, phoneNumber)
    }

    override fun playAudio(file: File) {
        IntentUtil.playAudio(mActivity, file)
    }

    override fun showMessage(messageRes: Int) {
        Snackbar.make(view!!, messageRes, Snackbar.LENGTH_LONG).show()
    }

    override fun deleteRecording(recording: Recording) {
        AlertDialog.Builder(activity!!)
            .setTitle(R.string.title_delete_recording)
            .setMessage(R.string.message_delete_recording)
            .setPositiveButton(R.string.action_yes) { _, _ -> mViewModel.deleteRecording(recording) }
            .setNegativeButton(R.string.action_no, null)
            .show()
    }

    override fun showMessage(message: String) {
        Snackbar.make(view!!, message, Snackbar.LENGTH_LONG).show()
    }
}
