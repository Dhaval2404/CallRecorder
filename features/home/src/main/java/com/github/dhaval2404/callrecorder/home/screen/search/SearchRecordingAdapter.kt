package com.github.dhaval2404.callrecorder.home.screen.search

import android.widget.Filter
import android.widget.Filterable
import com.github.dhaval2404.base.BaseAdapter
import com.github.dhaval2404.callrecorder.callrecord.data.model.Recording
import com.github.dhaval2404.callrecorder.home.R
import com.github.dhaval2404.callrecorder.home.databinding.AdapterInboxBinding
import com.github.dhaval2404.callrecorder.home.screen.inbox.InboxItemViewHolder
import com.github.dhaval2404.callrecorder.home.screen.inbox.InboxViewModel

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Jan 2020
 */
class SearchRecordingAdapter(private val viewModel: InboxViewModel) :
    BaseAdapter<Recording, AdapterInboxBinding, InboxItemViewHolder>(),
    Filterable {

    private val mRecordingList = ArrayList<Recording>()

    override fun getLayout() = R.layout.adapter_inbox

    override fun getViewHolder(binding: AdapterInboxBinding, viewType: Int): InboxItemViewHolder {
        return InboxItemViewHolder(viewModel, binding)
    }

    override fun onBindViewHolder(holder: InboxItemViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun refresh(list: List<Recording>) {
        mRecordingList.clear()
        mRecordingList.addAll(list)
        super.refresh(list)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString = charSequence.toString()
                val list = ArrayList<Recording>()
                if (charString.isEmpty()) {
                    list.addAll(mRecordingList)
                } else {
                    val query = charString.toLowerCase()
                    for (recording in mRecordingList) {
                        if (recording.name?.toLowerCase()?.contains(query) == true ||
                            recording.note?.toLowerCase()?.contains(query) == true ||
                            recording.phoneNumber.toLowerCase().contains(query)
                        ) {
                            list.add(recording)
                        }
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = list
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                itemList.clear()
                itemList.addAll(filterResults.values as List<Recording>)
                // refresh the list with filtered data
                notifyDataSetChanged()
            }
        }
    }
}
