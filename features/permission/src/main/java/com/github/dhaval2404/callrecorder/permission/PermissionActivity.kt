package com.github.dhaval2404.callrecorder.permission

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.dhaval2404.base.action.Actions
import com.github.dhaval2404.base.extension.launchActivity
import com.github.dhaval2404.callrecorder.permission.permission_request.PermissionFragment
import com.github.dhaval2404.callrecorder.permission.util.PermissionManager
import kotlinx.android.synthetic.main.activity_permission.*

class PermissionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        // Setup Adapter
        viewPager.adapter = PermissionAdapter(this)

        // Disable swipe
        viewPager.isUserInputEnabled = false

        // Set TabLayout
        pageIndicator.setViewPager(viewPager)
    }

    fun nextPage() {
        val currentItem = viewPager.currentItem
        if (currentItem < (viewPager.adapter?.itemCount ?: 0) - 1) {
            viewPager.setCurrentItem(currentItem + 1, true)
        } else {
            launchActivity(Actions.getHomeAction(this), clearStack = true)
        }
    }

    class PermissionAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        private val permissionList = PermissionManager.getPermissionList()

        override fun createFragment(position: Int): Fragment {
            val permission = permissionList[position]
            return PermissionFragment.getInstance(permission)
        }

        override fun getItemCount() = permissionList.size
    }
}
