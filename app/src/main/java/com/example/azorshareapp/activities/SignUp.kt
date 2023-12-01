package com.example.azorshareapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.azorshareapp.R
import com.example.azorshareapp.fragments.signUp.Email
import com.example.azorshareapp.fragments.signUp.OTP
import com.example.azorshareapp.fragments.signUp.Password
import com.example.azorshareapp.fragments.signUp.Username
import com.example.azorshareapp.models.AccountModel
import com.example.azorshareapp.utils.RegexUtils
import androidx.fragment.app.FragmentManager
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject

class SignUp : AppCompatActivity(){

    // Declare instance variables
    private lateinit var fragmentManager: FragmentManager
    private lateinit var email: String
    private lateinit var username: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Hide the action bar
        supportActionBar?.hide()

        // Initialize the fragment manager
        fragmentManager = supportFragmentManager

        // Add the email fragment to the container
        val fragmentTransaction = fragmentManager.beginTransaction()
        val emailFragment = Email()
        fragmentTransaction.add(R.id.container, emailFragment)
        fragmentTransaction.commit()
    }

    // Validate the user input
    fun validate(email: String, username: String, password: String, passwordConfirm: String): Boolean {
        if (password != passwordConfirm) {
            return false
        }
        if (username.length > 20) {
            return false
        }
        if (!RegexUtils.isEmailValid(email)) {
            return false
        }
        return true
    }

    // Create a new account
    @Throws(JSONException::class)
    fun createAccount(password: String) {
        val acc = AccountModel(email, username, password)
        val json = json(acc)
        val json2 = JSONObject()
        json2.put("username", username)

        TODO() //MAKE REQUEST TO CREATE ACCOUNT

        TODO() // MAKE REQUEST TO SEND OTP

        switchFragment("OTP", password)
    }

    // Finish the activity
    override fun finish() {
        val intent = Intent(this, SignIn::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finishAffinity()
    }

    // Convert the object to JSON
    private fun json(data: Any): String {
        val gson = Gson()
        return gson.toJson(data)
    }

    // Switch to the specified fragment
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
                fragmentTransaction.commit()
                this.password = input
            }
        }
    }

    fun sendOTP() {
        val json2 = JSONObject()
        try {
            json2.put("username", username)
        } catch (e: JSONException) {
            throw RuntimeException(e)
        }

        TODO() //MAKE REQUEST TO SEND OTP
    }
}
