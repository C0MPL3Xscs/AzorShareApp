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
            // Cria um novo intent para começar a activity SignIn
            val intent = Intent(this@Splash, SignIn::class.java)

            // Começa a atividade SignIn
            startActivity(intent)

            // Termina a atividade
            finish()
        }, 3000) // Delay de 3 segundos (3000 milissegundos)
    }
}
