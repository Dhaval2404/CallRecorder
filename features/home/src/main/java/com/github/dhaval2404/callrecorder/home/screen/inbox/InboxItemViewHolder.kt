package com.github.dhaval2404.callrecorder.home.screen.inbox

import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.callrecorder.callrecord.data.model.Recording
import com.github.dhaval2404.callrecorder.home.R
import com.github.dhaval2404.callrecorder.home.databinding.AdapterInboxBinding

class InboxItemViewHolder(val viewModel: InboxViewModel, val binding: AdapterInboxBinding) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            viewModel.playAudio(it.tag as Recording)
        }

        binding.toolbar.inflateMenu(R.menu.menu_recording)
    }

    fun bind(recording: Recording?) {
        binding.recording = recording

        val icon = getFavouriteIcon(recording?.isFavourite == true)
        binding.toolbar.menu.findItem(R.id.action_favourite).setIcon(icon)
        binding.toolbar.setOnMenuItemClickListener { item ->
            viewModel.handleMenuItemClick(item, recording!!)
            true
        }
    }

    private fun getFavouriteIcon(isFavourite: Boolean): Int {
        return if (isFavourite) {
            R.drawable.outline_favorite_24
        } else {
            R.drawable.outline_favorite_border_24
        }
    }
}
