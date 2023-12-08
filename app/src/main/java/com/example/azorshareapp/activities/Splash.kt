package com.example.azorshareapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.azorshareapp.R
import com.example.azorshareapp.services.network.REQUESTS
import org.json.JSONException
import org.json.JSONObject

class Splash : AppCompatActivity() {

    // The onCreate method is called when the activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Desativa a actionbar
        supportActionBar?.hide()

        // Define o layout para o splashscreen
        setContentView(R.layout.activity_splash_screen)

        // Chama o metodo principal
        main()
    }

    // Metodo principal
    private fun main() {
        // Create a new handler to delay the start of the next activity
        Handler().postDelayed({
            // Retrieve the token from cache (SharedPreferences or DataStore)
            val preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            val token = preferences.getString("token", null)

            // If there is a token in the cache, validate the token
            if (!token.isNullOrBlank()) {
                val request = REQUESTS()

                // Validate the token asynchronously
                request.validateToken(token, object : REQUESTS.LoginCallback {
                    override fun onResult(response: String): Boolean {
                        val jsonString = response
                        val jsonObject = JSONObject(jsonString)

                        // If the data is valid, open the Main activity
                        if (jsonObject.getString("rescode") == "0001") {
                            val mainIntent = Intent(this@Splash, Main::class.java)

                            // Start the Main activity
                            startActivity(mainIntent)
                        } else {
                            // If the token is not valid, open the SignIn activity
                            val signInIntent = Intent(this@Splash, SignIn::class.java)
                            startActivity(signInIntent)
                        }

                        // Finish the Splash activity
                        finish()
                        return true
                    }

                    override fun onError(response: String): Boolean {
                        // Handle the error, for example, open the SignIn activity
                        val signInIntent = Intent(this@Splash, SignIn::class.java)
                        startActivity(signInIntent)

                        // Finish the Splash activity
                        finish()
                        return false
                    }
                })
            } else {
                // If there is no token, open the SignIn activity
                val signInIntent = Intent(this@Splash, SignIn::class.java)
                startActivity(signInIntent)

                // Finish the Splash activity
                finish()
            }
        }, 3000) // Delay for 3 seconds (3000 milliseconds)
    }

}
