package com.github.dhaval2404.callrecorder.home.screen.search

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.github.dhaval2404.base.BaseDialogFragment
import com.github.dhaval2404.callrecorder.callrecord.data.model.Recording
import com.github.dhaval2404.callrecorder.home.R
import com.github.dhaval2404.callrecorder.home.databinding.FragmentSearchRecordingsBinding
import com.github.dhaval2404.callrecorder.home.screen.inbox.InboxNavigator
import com.github.dhaval2404.callrecorder.home.screen.inbox.InboxViewModel
import com.github.dhaval2404.callrecorder.home.util.IntentUtil
import com.google.android.material.snackbar.Snackbar
import java.io.File
import kotlinx.android.synthetic.main.fragment_search_recordings.*
import org.koin.android.ext.android.get

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Jan 2020
 */
class SearchRecordingFragment :
    BaseDialogFragment<FragmentSearchRecordingsBinding, InboxViewModel>(),
    InboxNavigator {

    companion object {

        fun getInstance() = SearchRecordingFragment()
    }

    override fun getTheme() = R.style.AppTheme_Dialog_Light

    override fun getLayout() = R.layout.fragment_search_recordings

    override fun getViewModel(): InboxViewModel = get()

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchEt.requestFocus()

        mViewDataBinding.viewModel = mViewModel

        recordingRV.adapter = mViewModel.getSearchAdapter()

        mViewModel.setNavigator(this)
        mViewModel.showAllRecordings(this, true)

        backBtn.setOnClickListener {
            dismiss()
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
