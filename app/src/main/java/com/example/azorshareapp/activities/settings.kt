package com.example.azorshareapp.activities

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.azorshareapp.fragments.settings.AccountFragment
import com.example.azorshareapp.fragments.settings.SecurityFragment
import com.example.azorshareapp.fragments.settings.PrivacyFragment
import com.example.azorshareapp.fragments.settings.NotificationsFragment
import com.example.azorshareapp.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var accountFragment: Fragment
    private lateinit var securityFragment: Fragment
    private lateinit var privacyFragment: Fragment
    private lateinit var notificationsFragment: Fragment
    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        token = preferences.getString("token", null).toString()

        // Initialize fragments
        accountFragment = AccountFragment()
        securityFragment = SecurityFragment()
        privacyFragment = PrivacyFragment()
        notificationsFragment = NotificationsFragment()

        // Set up button listeners
        findViewById<Button>(R.id.account).setOnClickListener {
            switchFragment(accountFragment)
        }

        findViewById<Button>(R.id.security).setOnClickListener {
            switchFragment(securityFragment)
        }

        findViewById<Button>(R.id.privacy).setOnClickListener {
            switchFragment(privacyFragment)
        }

        findViewById<Button>(R.id.notifications).setOnClickListener {
            switchFragment(notificationsFragment)
        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.settingsContainer, fragment).commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
