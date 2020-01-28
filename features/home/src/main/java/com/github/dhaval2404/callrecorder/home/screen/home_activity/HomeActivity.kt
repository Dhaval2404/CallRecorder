package com.github.dhaval2404.callrecorder.home.screen.home_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.github.dhaval2404.callrecorder.callrecord.service.ContactSyncService
import com.github.dhaval2404.callrecorder.home.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }

        // Sync Contact
        ContactSyncService.startService(this)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        // this.navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavView.setupWithNavController(navHostFragment.navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp() ?: false
    }
}
