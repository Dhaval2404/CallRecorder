package com.github.dhaval2404.callrecorder.permission.permission_request

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.github.dhaval2404.base.BaseFragment
import com.github.dhaval2404.base.extension.withArgs
import com.github.dhaval2404.callrecorder.permission.PermissionActivity
import com.github.dhaval2404.callrecorder.permission.R
import com.github.dhaval2404.callrecorder.permission.databinding.FragmentPermissionBinding
import com.github.dhaval2404.callrecorder.permission.util.PermissionManager
import org.koin.android.ext.android.get

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Jan 2020
 */
open class PermissionFragment : BaseFragment<FragmentPermissionBinding, PermissionViewModel>(),
    PermissionNavigator {

    companion object {
        private const val EXTRA_PERMISSION = "permission"
        private const val ROLE_MANAGER_REQUEST_ID = 101

        fun getInstance(permission: Permission) = PermissionFragment().withArgs {
            putParcelable(EXTRA_PERMISSION, permission)
        }
    }

    override fun getLayout() =
        R.layout.fragment_permission

    override fun getViewModel(): PermissionViewModel = get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.setNavigator(this)
        mViewDataBinding.viewModel = mViewModel
        mViewDataBinding.permission = arguments?.getParcelable(EXTRA_PERMISSION) as Permission?
    }

    override fun showNextPermission() {
        (activity as PermissionActivity).nextPage()
    }

    override fun requestRoleManager() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            val intent = PermissionManager.getRoleManager(context!!)
                .createRequestRoleIntent(PermissionManager.ROLE_CALL_SCREENING)
            startActivityForResult(intent, ROLE_MANAGER_REQUEST_ID)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ROLE_MANAGER_REQUEST_ID && resultCode == Activity.RESULT_OK) {
            showNextPermission()
        }
    }
}
