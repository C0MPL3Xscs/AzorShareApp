package com.example.azorshareapp.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.azorshareapp.R
import com.example.azorshareapp.models.LogInData
import com.example.azorshareapp.services.network.NetworkTask
import com.example.azorshareapp.services.network.NetworkTaskCallback
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject

class SignIn : AppCompatActivity(), NetworkTaskCallback {

    // Variables
    private var passwordShown = false
    private lateinit var usernameField: EditText
    private lateinit var passwordField: EditText
    private lateinit var progressDialog: ProgressDialog

    // Called when activity is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Initialize variables with corresponding views
        usernameField = findViewById(R.id.Username)
        passwordField = findViewById(R.id.Password)

        // Initialize progressDialog
        progressDialog = ProgressDialog(this)

        // Hide action bar
        supportActionBar?.hide()

        // Attach click listeners to buttons
        findViewById<View>(R.id.button6).setOnClickListener {
            toggleView() // Toggle password visibility
        }

        findViewById<View>(R.id.button2).setOnClickListener {
            signUp() // Launch Sign Up activity
        }

        findViewById<View>(R.id.button).setOnClickListener {
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()
            if (username.isEmpty() || password.isEmpty()) {
                error("Username or password can't be empty")
            } else {
                // Send login request to server
                login()
            }
        }

        findViewById<View>(R.id.ForgotPassword).setOnClickListener {
            forgotPassword() // Launch Forgot Password activity
        }
    }

    // Launch Sign Up activity
    fun signUp() {
        val t = Intent(this, SignUp::class.java)
        startActivity(t)
    }

    // Launch Forgot Password activity
    fun forgotPassword() {
        val f = Intent(this, ForgotPassword::class.java)
        startActivity(f)
    }

    // Toggle password visibility
    fun toggleView() {
        val passwordEditText = findViewById<EditText>(R.id.Password)
        passwordShown = if (!passwordShown) {
            passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
            true
        } else {
            passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            false
        }
        // Move cursor to the end
        passwordEditText.setSelection(passwordEditText.text.length)
    }

    // Convert data to JSON string
    private fun json(data: Any): String {
        val gson = Gson()
        return gson.toJson(data)
    }

    // Send login request to server
    fun login() {
        // Show loading animation
        progressDialog.setCancelable(false)
        progressDialog.show()
        val username = usernameField.text.toString()
        val password = passwordField.text.toString()
        val data = LogInData(username, password)
        val json = json(data)

        val networkTask = NetworkTask(this, json, "login")
        networkTask.execute()
    }

    // Called when network task is complete
    override fun onNetworkTaskComplete(result: Boolean, jsonResponse: String) {
        // Close loading animation
        progressDialog.dismiss()
        if (result) {
            try {
                // Parse response JSON and check response code
                val jsonObject = JSONObject(jsonResponse)
                val header = jsonObject.getJSONObject("header")
                val resCode = header.getString("resCode")
                when {
                    resCode == "00000" -> startApp() // Launch Main activity
                    resCode == "00003" -> error("Incorrect username or password") // Incorrect username or password
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        } else {
            println("Network request failed") // Network error
        }
    }

    // Launch Main activity and finish this activity
    private fun startApp() {
        val m = Intent(this, Main::class.java)
        startActivity(m)
        finish()
    }

    private fun error(message: String) {
        usernameField.setBackgroundResource(R.drawable.wrong_input)
        passwordField.setBackgroundResource(R.drawable.wrong_input)
        val errorMessage = findViewById<TextView>(R.id.ErrorMessage)
        errorMessage.text = message
    }
}
