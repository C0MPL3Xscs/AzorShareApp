package com.example.azorshareapp.services.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

class REQUESTS {

    private val domain = "http://azorshare.xyz/API/"

    interface LoginCallback {
        fun onResult(response: String)
    }

    // Function for login request
    fun login(email: String, password: String, callback: LoginCallback) {
        GlobalScope.launch(Dispatchers.IO) {
            val url = URL(domain + "test/")
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "POST"
            connection.doOutput = true

            val postData = "email=$email&password=$password"
            val postDataBytes = postData.toByteArray(StandardCharsets.UTF_8)

            connection.outputStream.use { os ->
                os.write(postDataBytes)
            }

            val responseCode = connection.responseCode
            println("Login request response code: $responseCode")

            // Read the response
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = reader.use { it.readText() }
            println("Login request response: $response")

            connection.disconnect()

            // Return the result via callback
            withContext(Dispatchers.Main) {
                callback.onResult(response)
            }
        }

        // Helper function to parse rescode from JSON response
        fun parseRescodeFromJson(jsonResponse: String): String {
            try {
                val jsonObject = JSONObject(jsonResponse)
                return jsonObject.getString("rescode")
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            // Return an appropriate value if parsing fails
            return ""
        }

        // Function for sending OTP request
        fun sendOtp(username: String) {
            // Implement your logic for send OTP request here
        }

        // Function for validating OTP request
        fun validateOtp(username: String, otp: String) {
            // Implement your logic for validate OTP request here
        }

        // Function for password recovery request
        fun recoveryPassword(username: String, password: String) {
            // Implement your logic for password recovery request here
        }

        // Function for validating username request
        fun validateUsername(username: String) {
            // Implement your logic for validate username request here
        }

        // Function for validating email request
        fun validateEmail(email: String) {
            // Implement your logic for validate email request here
        }

        // Function for creating an account request
        fun createAccount(username: String, email: String, password: String) {
            val url = URL(domain + "create_account/")
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "POST"
            connection.doOutput = true

            val postData = "username=$username&email=$email&password=$password"
            val postDataBytes = postData.toByteArray(StandardCharsets.UTF_8)

            connection.outputStream.use { os ->
                os.write(postDataBytes)
            }

            val responseCode = connection.responseCode
            println("Create Account request response code: $responseCode")

            // Read the response
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = reader.use { it.readText() }
            println("Create Account request response: $response")

            connection.disconnect()
        }
    }
}
