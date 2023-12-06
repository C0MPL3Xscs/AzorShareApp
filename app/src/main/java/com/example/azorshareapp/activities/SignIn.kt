package com.example.azorshareapp.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.azorshareapp.services.network.REQUESTS
import com.example.azorshareapp.R
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject


class SignIn : AppCompatActivity() {

    // Variables
    private var passwordShown = false
    private lateinit var EmailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Inicializa as variavies com as respetivas views
        EmailField = findViewById(R.id.Email)
        passwordField = findViewById(R.id.Password)
        progressDialog = ProgressDialog(this)

        // Desativa a actionbar
        supportActionBar?.hide()

        // Listeners para os botões
        findViewById<View>(R.id.button6).setOnClickListener {
            toggleView() // Ativa/Desativa a visibilidade da palavra-passe
        }

        findViewById<View>(R.id.button2).setOnClickListener {
            signUp() // Abre a signup activity
        }

        findViewById<View>(R.id.button).setOnClickListener {
            val email = EmailField.text.toString()
            val password = passwordField.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                error("Email ou palavra-passe não podem estar vazios")
            } else {
                // Envia um login request ao servidor
                login()
            }
        }

        findViewById<View>(R.id.ForgotPassword).setOnClickListener {
            forgotPassword() //  Abre a forgotPassword activity
        }
    }

    // Abre a signup activity
    fun signUp() {
        val t = Intent(this, SignUp::class.java)
        startActivity(t)
    }

    //  Abre a forgotPassword activity
    fun forgotPassword() {
        val f = Intent(this, ForgotPassword::class.java)
        startActivity(f)
    }

    // Ativa/Desativa a visibilidade da palavra-passe
    fun toggleView() {
        val passwordEditText = findViewById<EditText>(R.id.Password)
        passwordShown = if (!passwordShown) {
            passwordEditText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
            true
        } else {
            passwordEditText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            false
        }
        // Move o crusor para o fim da string
        passwordEditText.setSelection(passwordEditText.text.length)
    }

    // Envia um request de login ao servidor
    fun login() {
        // Começa e mostra a animação de loading
        progressDialog.setCancelable(false)
        progressDialog.show()

        val email = EmailField.text.toString()
        val password = passwordField.text.toString()

        val request = REQUESTS()

        request.login(email, password, object : REQUESTS.LoginCallback {
            override fun onResult(response: String): Boolean {
                val jsonString = response
                val jsonObject = JSONObject(jsonString)
                progressDialog.dismiss()
                //Caso os dados sejam validos
                if (jsonObject.getString("rescode") == "0001") {
                    startApp(email)
                    return true
                } else {
                    error("Palavra-passe ou nome de utilizador invalidos")
                }
                return false
            }
            override fun onError(response: String): Boolean {
                progressDialog.dismiss()
                error("Erro, tente novamente mais tarde")
                return false
            }
        })
    }

    // Abre o ecrâ principal e termina a activity de login
    private fun startApp(email : String) {
        val request = REQUESTS()

        request.generateToken(email, object : REQUESTS.LoginCallback {
            override fun onResult(response: String): Boolean {
                val jsonString = response
                val jsonObject = JSONObject(jsonString)

                //Caso os dados sejam validos
                if (jsonObject.getString("rescode") == "0001") {
                    val token = jsonObject.getString("token")
                    val preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                    val editor = preferences.edit()
                    editor.putString("token", token)
                    editor.apply()
                    return true
                } else {
                    error("Palavra-passe ou nome de utilizador invalidos")
                }
                return false
            }
            override fun onError(response: String): Boolean {
                return false
            }
        })
        val m = Intent(this, Main::class.java)
        startActivity(m)
        finish()
    }

    // Mostra/altera menssagem de erro
    private fun error(message: String) {
        EmailField.setBackgroundResource(R.drawable.wrong_input)
        passwordField.setBackgroundResource(R.drawable.wrong_input)
        val errorMessage = findViewById<TextView>(R.id.ErrorMessage)
        errorMessage.text = message
    }
}
