package com.example.azorshareapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.azorshareapp.R

class Splash : AppCompatActivity() {

    // The onCreate method is called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Hide the action bar (if present)
        supportActionBar?.hide()

        // Set the activity layout
        setContentView(R.layout.activity_splash_screen)

        // Call the Main method
        main()
    }

    // The Main method handles the delay and the start of the SignIn activity
    private fun main() {
        // Create a new Handler object to delay the start of the next activity
        Handler().postDelayed({
            // Create a new Intent object to start the SignIn activity
            val intent = Intent(this@Splash, SignIn::class.java)

            // Start the SignIn activity
            startActivity(intent)

            // Finish the Splash activity so that it cannot be returned to
            finish()
        }, 3000) // Delay for 3 seconds (3000 milliseconds)
    }
}
