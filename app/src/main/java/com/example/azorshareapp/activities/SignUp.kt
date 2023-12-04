package com.example.azorshareapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.azorshareapp.R
import com.example.azorshareapp.fragments.signUp.Email
import com.example.azorshareapp.fragments.signUp.OTP
import com.example.azorshareapp.fragments.signUp.Password
import com.example.azorshareapp.fragments.signUp.Username
import com.example.azorshareapp.utils.RegexUtils
import androidx.fragment.app.FragmentManager
import com.example.azorshareapp.services.network.REQUESTS
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject

class SignUp : AppCompatActivity(){

    // Declare instance variables
    private lateinit var fragmentManager: FragmentManager
    private lateinit var email: String
    private lateinit var username: String
    private lateinit var password: String
    val request = REQUESTS()

    override fun onCreate(savedInstanceState: Bundle?) {

        // Chama a superclasse e define o layout para esta activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Desativa a actionbar
        supportActionBar?.hide()

        // Inicializa o fragment manager
        fragmentManager = supportFragmentManager

        // Adiciona o fragmento do email ao contentor
        val fragmentTransaction = fragmentManager.beginTransaction()
        val emailFragment = Email()
        fragmentTransaction.add(R.id.container, emailFragment)
        fragmentTransaction.commit()
    }

    // Cria uma conta
    @Throws(JSONException::class)
    fun createAccount() : Boolean {

        var success = false

        //request ao servidor para criar conta
        request.createAccount(this.username,this.email,this.password,object : REQUESTS.LoginCallback {
            override fun onResult(response: String): Boolean {
                val jsonString = response
                val jsonObject = JSONObject(jsonString)
                //Caso tenha sido criada corretamente
                if (jsonObject.getString("rescode") == "0001") {
                    finish()
                    success = true
                    return true
                }
                return false
            }
            override fun onError(response: String): Boolean {
                return false
            }
        })

        return success

    }

    //Retorna a variavel local email
    fun getEmail(): String {
        return this.email;
    }

    // Termina a atividade
    override fun finish() {
        val intent = Intent(this, SignIn::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finishAffinity()
    }

    // Muda de fragmento
    fun switchFragment(fragmentName: String, input: String) {
        val fragmentTransaction = fragmentManager.beginTransaction()

        when (fragmentName) {
            "Email" -> {
                val emailFragment = Email()
                fragmentTransaction.replace(R.id.container, emailFragment)
                fragmentTransaction.commit()
            }
            "Username" -> {
                val usernameFragment = Username()
                fragmentTransaction.replace(R.id.container, usernameFragment)
                fragmentTransaction.commit()
                email = input
            }
            "Password" -> {
                val passwordFragment = Password()
                fragmentTransaction.replace(R.id.container, passwordFragment)
                fragmentTransaction.commit()
                username = input
            }
            "OTP" -> {
                val otpFragment = OTP()
                fragmentTransaction.replace(R.id.container, otpFragment)
                password = input
                sendOTP();
                fragmentTransaction.commit()
            }
        }
    }

    // Envia um OTP para o email que o utilizador inserir
    fun sendOTP() {

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
