package com.github.dhaval2404.callrecorder.home.screen.favourite

import android.os.Bundle
import com.github.dhaval2404.callrecorder.home.screen.inbox.BaseInboxFragment

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Jan 2020
 */
class FavouriteFragment : BaseInboxFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.showFavouriteRecordings(this)
    }
}
