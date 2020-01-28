package com.github.dhaval2404.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Generic Base Adapter
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 22 January 2019
 */
abstract class BaseAdapter<T, VDB : ViewDataBinding, VH : RecyclerView.ViewHolder>(
    protected val itemList: MutableList<T> = ArrayList()
) : RecyclerView.Adapter<VH>() {

    companion object {

        fun <VDB : ViewDataBinding> inflate(parent: ViewGroup, @LayoutRes layout: Int): VDB {
            val inflater = LayoutInflater.from(parent.context)
            return DataBindingUtil.inflate(inflater, layout, parent, false)
        }
    }

    constructor() : this(ArrayList())

    @LayoutRes
    protected abstract fun getLayout(): Int

    override fun getItemCount() = itemList.size

    open fun getItem(position: Int) = itemList[position]

    protected open fun getViewHolder(binding: VDB): VH {
        return object : RecyclerView.ViewHolder(binding.root) {} as VH
    }

    protected open fun getViewHolder(binding: VDB, viewType: Int): VH {
        return getViewHolder(binding)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val viewDataBinding: VDB = BaseAdapter.inflate(parent, getLayout())
        return getViewHolder(viewDataBinding, viewType)
    }

    open fun refresh(list: List<T>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }

    fun notifyItemChanged(vararg index: Int) {
        index.forEach {
            notifyItemChanged(it)
        }
    }
}
