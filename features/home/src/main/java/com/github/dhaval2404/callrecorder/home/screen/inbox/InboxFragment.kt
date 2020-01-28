package com.github.dhaval2404.callrecorder.home.screen.inbox

import android.os.Bundle

/**
 * InboxFragment
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Jan 2020
 */
class InboxFragment : BaseInboxFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.showAllRecordings(this)
    }
}
