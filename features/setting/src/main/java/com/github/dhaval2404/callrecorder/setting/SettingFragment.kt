package com.github.dhaval2404.callrecorder.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.github.dhaval2404.base.extension.setTheme
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity

/**
 * TODO: Add Class Info
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Jan 2020
 */
class SettingFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences_setting)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setPreferenceClickListener(R.string.key_share_app) {
            shareApp()
        }

        setPreferenceClickListener(R.string.key_open_source_licences) {
            showOssLicensesMenuActivity()
        }

        setPreferenceClickListener(R.string.key_privacy_policy) {
            openURL(getString(R.string.url_privacy_policy))
        }

        setPreferenceClickListener(R.string.key_terms_of_use) {
            openURL(getString(R.string.url_terms_of_use))
        }

        findPreference<Preference>(R.string.key_dark_theme)
            ?.setOnPreferenceChangeListener { preference, newValue ->
                context?.setTheme(newValue as Boolean)
                true
            }

        findPreference<Preference>(R.string.key_app_language)
            ?.setOnPreferenceChangeListener { preference, newValue ->
                preference.setDefaultValue(newValue)
                true
            }

        findPreference<Preference>(R.string.key_app_version)?.summary =
            "v${getAppVersion(context!!)}"
    }

    private fun setPreferenceClickListener(keyRes: Int, listener: () -> Unit) {
        findPreference<Preference>(keyRes)
            ?.setOnPreferenceClickListener {
                listener.invoke()
                true
            }
    }

    private fun <T : Preference> findPreference(keyRes: Int): T? {
        return findPreference<T>(getString(keyRes))
    }

    private fun showOssLicensesMenuActivity() {
        val intent = Intent(activity!!, OssLicensesMenuActivity::class.java)
        startActivity(intent)
    }

    private fun getAppVersion(context: Context): String {
        return context.packageManager
            .getPackageInfo(context.packageName, 0).versionName
    }

    private fun shareApp() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.message_share_intent))
        intent.type = "text/plain"
        startActivity(intent)
    }

    fun openURL(url: String) {
        val link = Uri.parse(url)
        CustomTabsIntent.Builder()
            .build()
            .launchUrl(activity!!, link)
    }
}
