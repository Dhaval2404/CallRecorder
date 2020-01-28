package com.github.dhaval2404.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel

/**
 * Parent Fragment for all the app Fragment
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 22 January 2019
 */
abstract class BaseDialogFragment<VDB : ViewDataBinding, VM : ViewModel> : DialogFragment() {

    protected lateinit var mRootView: View

    protected lateinit var mViewDataBinding: VDB
    protected lateinit var mViewModel: VM

    protected lateinit var mActivity: FragmentActivity

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun getViewModel(): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
        mActivity = activity as FragmentActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        mRootView = mViewDataBinding.root
        return mRootView
    }

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, javaClass.simpleName)
    }
}
