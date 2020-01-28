package com.github.dhaval2404.callrecorder.home.screen.inbox

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.base.BaseAdapter
import com.github.dhaval2404.callrecorder.callrecord.data.model.Recording
import com.github.dhaval2404.callrecorder.home.R
import com.github.dhaval2404.callrecorder.home.databinding.AdapterInboxHeaderBinding
import com.github.dhaval2404.callrecorder.home.util.TimeUtil
import java.util.Calendar
import kotlin.collections.ArrayList

/**
 * Inbox Adapter
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Jan 2020
 */
class InboxAdapter(private val viewModel: InboxViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEMS = 1
    }

    private val itemList: MutableList<InboxItem> = ArrayList()

    override fun getItemCount() = itemList.size

    override fun getItemViewType(position: Int): Int {
        val item = itemList[position]
        return if (!item.header.isNullOrBlank())
            TYPE_HEADER
        else
            TYPE_ITEMS
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                HeaderViewHolder(BaseAdapter.inflate(parent, R.layout.adapter_inbox_header))
            }
            TYPE_ITEMS -> {
                InboxItemViewHolder(viewModel, BaseAdapter.inflate(parent, R.layout.adapter_inbox))
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]
        if (holder is InboxItemViewHolder) {
            holder.bind(item.recording)
        } else if (holder is HeaderViewHolder) {
            holder.bind(item.header)
        }
    }

    inner class HeaderViewHolder(val binding: AdapterInboxHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(header: String?) {
            binding.header = header
        }
    }

    inner class InboxItem(
        val header: String? = null,
        val recording: Recording? = null
    )

    fun refresh(list: List<Recording>) {
        itemList.clear()

        val calendar1 = Calendar.getInstance()
        val todayList = list.filter { TimeUtil.isSameDay(calendar1, it.createdAt) }

        val calendar11 = Calendar.getInstance()
        calendar11.add(Calendar.DAY_OF_MONTH, -1)
        val yesterdayList = list.filter { TimeUtil.isSameDay(calendar11, it.createdAt) }

        val olderList = list.filter {
            !TimeUtil.isSameDay(calendar1, it.createdAt) &&
                    !TimeUtil.isSameDay(calendar11, it.createdAt)
        }

        if (todayList.isNotEmpty()) {
            itemList.add(InboxItem(header = "Today"))
            itemList.addAll(todayList.map { InboxItem(recording = it) })
        }

        if (yesterdayList.isNotEmpty()) {
            itemList.add(InboxItem(header = "Yesterday"))
            itemList.addAll(yesterdayList.map { InboxItem(recording = it) })
        }

        if (olderList.isNotEmpty()) {
            itemList.add(InboxItem(header = "Older"))
            itemList.addAll(olderList.map { InboxItem(recording = it) })
        }

        notifyDataSetChanged()
    }
}
