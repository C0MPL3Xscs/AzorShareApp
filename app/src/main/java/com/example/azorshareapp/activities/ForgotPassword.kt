package com.example.azorshareapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.azorshareapp.R
import com.example.azorshareapp.fragments.forgotPassword.OTPVerify
import com.example.azorshareapp.fragments.forgotPassword.PasswordRecovery
import com.example.azorshareapp.fragments.forgotPassword.EmailRecovery
import com.example.azorshareapp.services.network.REQUESTS
import org.json.JSONException
import org.json.JSONObject

class ForgotPassword : AppCompatActivity(){

    private var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        // Inicializa o fragment manager e transaction
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Chama a superclasse e define o layout para esta activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Desativa a actionbar
        supportActionBar?.hide()

        // Inicializa e adiciona o usernameFragment
        val usernameFragment = EmailRecovery()
        fragmentTransaction.add(R.id.container, usernameFragment)
        fragmentTransaction.commit()
    }

    // Metodo para mostrar o fragmento OTP
    fun otpView() {
        // Initialize fragment manager and transaction
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Create new OTPVerify fragment and replace the current one with it
        val otpFragment = OTPVerify()
        fragmentTransaction.replace(R.id.container, otpFragment)
        fragmentTransaction.commit()
    }

    // Metodo para mostrar o fragmento recuperar palavrapasse
    fun changePassword() {
        // Initialize fragment manager and transaction
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Create new PasswordRecovery fragment and replace the current one with it
        val passwordFragment = PasswordRecovery()
        fragmentTransaction.replace(R.id.container, passwordFragment)
        fragmentTransaction.commit()
    }

    // Metodo de finalização
    override fun finish() {
        val intent = Intent(this, SignIn::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finishAffinity()
    }

    // Guarda o valor do email na variavel local
    fun setEmail(email: String) {
        this.email = email
    }

    // Retorna o valor da variavel local email
    fun getEmail(): String {
        return this.email
    }

    // Envia um OTP para o email que o utilizador inserir
    fun sendOTP() {

        val request = REQUESTS()

        request.sendOtp(this.email, object : REQUESTS.LoginCallback {
            override fun onResult(response: String): Boolean {
                return true
            }
            override fun onError(response: String): Boolean {
                return true
            }
        })
    }

}
