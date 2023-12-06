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
        // Cria um novo handler para atrazar o inicio da proxima atividade
        Handler().postDelayed({

            // Retrieve the token from cache (SharedPreferences or DataStore)
            val preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            val token = preferences.getString("token", null)

            //Se tiver token guardado no cache valida o token e caso seja valido abre o main caso contrario abre o SignIn
            if (!token.isNullOrBlank()) {
                val request = REQUESTS()

                request.validadeToken(token, object : REQUESTS.LoginCallback {
                    override fun onResult(response: String): Boolean {
                        val jsonString = response
                        val jsonObject = JSONObject(jsonString)

                        //Caso os dados sejam validos
                        if (jsonObject.getString("rescode") == "0001") {
                            val Mainintent = Intent(this@Splash, Main::class.java)

                            // Começa a atividade Main
                            startActivity(Mainintent)
                        } else {
                            // Se o token não for válido, abre a atividade SignIn
                            val signInIntent = Intent(this@Splash, SignIn::class.java)
                            startActivity(signInIntent)
                        }
                        return true
                    }override fun onError(response: String): Boolean {
                        val signInIntent = Intent(this@Splash, SignIn::class.java)
                        startActivity(signInIntent)
                        return false
                    }
                })
            } else {
                // Se não houver token, abre a atividade SignIn
                val signInIntent = Intent(this@Splash, SignIn::class.java)
                startActivity(signInIntent)
            }

            // Termina a atividade
            finish()
        }, 3000) // Delay de 3 segundos (3000 milissegundos)
    }
}
