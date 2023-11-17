package com.example.azorshareapp.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.azorshareapp.fragments.main_screen.discover.Discover
import com.example.azorshareapp.fragments.main_screen.notifications.Notifications
import com.example.azorshareapp.fragments.main_screen.profile.Profile
import com.example.azorshareapp.fragments.main_screen.search.Search
import com.example.azorshareapp.fragments.main_screen.spaces.Spaces
import com.example.azorshareapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView

class Main : AppCompatActivity() {

    // declare variables for bottom navigation and fragments
    private lateinit var bottomNavigationView: BottomNavigationView
    private val discover = Discover()
    private val notifications = Notifications()
    private val profile = Profile()
    private val search = Search()
    private val spaces = Spaces()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set content view and hide action bar
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        // get reference to bottom navigation view and set initial fragment
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        supportFragmentManager.beginTransaction().replace(R.id.Container, discover).commit()

        // set on item selected listener for bottom navigation view
        bottomNavigationView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.notfications -> {
                        supportFragmentManager.beginTransaction().replace(R.id.Container, notifications).commit()
                        return true
                    }
                    R.id.profile -> {
                        supportFragmentManager.beginTransaction().replace(R.id.Container, profile).commit()
                        return true
                    }
                    R.id.search -> {
                        supportFragmentManager.beginTransaction().replace(R.id.Container, search).commit()
                        return true
                    }
                    R.id.spaces -> {
                        supportFragmentManager.beginTransaction().replace(R.id.Container, spaces).commit()
                        return true
                    }
                }
                return false
            }
        })

        // set on click listener for floating action button
        val myFab: FloatingActionButton = findViewById(R.id.Discovery)
        myFab.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.Container, discover).commit()
        }
    }
}
