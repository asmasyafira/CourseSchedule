package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder
import com.dicoding.courseschedule.util.NightMode
import java.util.Locale

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        val pref = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        pref?.setOnPreferenceChangeListener { _, newValue ->
            val nightMode = NightMode.valueOf(newValue.toString().uppercase(Locale.US))
            updateTheme(nightMode.value)
            true
        }
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val notification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        notification?.setOnPreferenceChangeListener { _, _ ->
            val dailyReminder = DailyReminder()
            val notificationPreference = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
            notificationPreference?.setOnPreferenceChangeListener { _, newValue ->
                val notificationEnabled = newValue as Boolean
                if (notificationEnabled) {
                    dailyReminder.setDailyReminder(requireContext())
                } else {
                    dailyReminder.cancelAlarm(requireContext())
                }
                true
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}