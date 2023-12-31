package com.example.azorshareapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
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
    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Define o contentView e desativa a actionbar
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        token = preferences.getString("token", null).toString()

        // Referencia do "bottomNavigation" e definir como fragmento inicial
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        supportFragmentManager.beginTransaction().replace(R.id.Container, discover).commit()

        // Define os listeners para os botoes na bottom navigation bar
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

        // Define o listener para o botao flutuante da bottom navigation bar
        val myFab: FloatingActionButton = findViewById(R.id.Discovery)
        myFab.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.Container, discover).commit()
        }
    }

    fun logOUt(){
        val preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("token", null)
        editor.apply()
        val mainIntent = Intent(this@Main, SignIn::class.java)
        startActivity(mainIntent)
        finish()
    }

    fun OpenSettings(){
        val mainIntent = Intent(this@Main, SettingsActivity::class.java)
        startActivity(mainIntent)
    }

    fun getToken(): String {
        return this.token
    }
}
