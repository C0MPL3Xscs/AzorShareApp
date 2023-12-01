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

    // onCreate method
    override fun onCreate(savedInstanceState: Bundle?) {
        // Initialize fragment manager and transaction
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Call superclass method and set the layout for this activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Hide the action bar if it exists
        supportActionBar?.hide()

        // Initialize and add UsernameRecovery fragment
        val usernameFragment = EmailRecovery()
        fragmentTransaction.add(R.id.container, usernameFragment)
        fragmentTransaction.commit()
    }

    // Method to switch to a new fragment
    fun switchFragment(fragment: String) {
        // Initialize fragment manager and transaction
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Create new UsernameRecovery fragment and replace the current one with it
        val usernameFragment = EmailRecovery()
        fragmentTransaction.replace(R.id.container, usernameFragment)
        fragmentTransaction.commit()
    }

    // Method to show the OTPVerify fragment
    fun otpView() {
        // Initialize fragment manager and transaction
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Create new OTPVerify fragment and replace the current one with it
        val otpFragment = OTPVerify()
        fragmentTransaction.replace(R.id.container, otpFragment)
        fragmentTransaction.commit()
    }

    // Method to show the PasswordRecovery fragment
    fun changePassword() {
        // Initialize fragment manager and transaction
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Create new PasswordRecovery fragment and replace the current one with it
        val passwordFragment = PasswordRecovery()
        fragmentTransaction.replace(R.id.container, passwordFragment)
        fragmentTransaction.commit()
    }

    // Override the default finish method to prevent it from being called accidentally
    override fun finish() {
        val intent = Intent(this, SignIn::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finishAffinity()
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getEmail(): String {
        return this.email
    }


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
