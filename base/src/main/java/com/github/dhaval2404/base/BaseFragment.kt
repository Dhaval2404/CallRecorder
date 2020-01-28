package com.github.dhaval2404.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel

/**
 * Parent Fragment for all the app Fragment
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 22 January 2019
 */
abstract class BaseFragment<VDB : ViewDataBinding, VM : ViewModel> : Fragment() {

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

    open fun onCreateView() {}

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
        onCreateView()
        return mRootView
    }
}
