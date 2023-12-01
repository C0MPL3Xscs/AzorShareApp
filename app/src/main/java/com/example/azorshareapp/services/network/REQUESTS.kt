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
        fun onResult(response: String): Boolean
        fun onError(response: String): Boolean
    }

    // Function for login request
    fun login(email: String, password: String, callback: LoginCallback) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL(domain + "login/?email=$email&password=$password")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.doOutput = true

                val responseCode = connection.responseCode
                println("Login request response code: $responseCode")

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }
                    println("Login request response: $response")

                    // Return the result via callback
                    withContext(Dispatchers.Main) {
                        callback.onResult(response)
                    }
                } else {
                    // Handle non-OK response codes
                    withContext(Dispatchers.Main) {
                        callback.onError("HTTP error: $responseCode")
                    }
                }

                connection.disconnect()

            } catch (e: Exception) {
                // Handle any other exceptions
                withContext(Dispatchers.Main) {
                    callback.onError("An error occurred: ${e.message}")
                }
            }
        }
    }

    // Function for sending OTP request
    fun sendOtp(email: String, callback: LoginCallback) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL(domain + "sendotp/?email=$email")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.doOutput = true

                val responseCode = connection.responseCode
                println("Login request response code: $responseCode")

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }
                    println("Login request response: $response")

                    // Return the result via callback
                    withContext(Dispatchers.Main) {
                        callback.onResult(response)
                    }
                } else {
                    // Handle non-OK response codes
                    withContext(Dispatchers.Main) {
                        callback.onError("HTTP error: $responseCode")
                    }
                }
                connection.disconnect()

            } catch (e: Exception) {
                // Handle any other exceptions
                withContext(Dispatchers.Main) {
                    callback.onError("An error occurred: ${e.message}")
                }
            }
        }
    }

    // Function for validating OTP request
    fun validateOtp(email: String, otp: String, callback: LoginCallback) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL(domain + "verifyotp/?email=$email&otp=$otp")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.doOutput = true

                val responseCode = connection.responseCode
                println("Login request response code: $responseCode")

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }
                    println("Login request response: $response")

                    // Return the result via callback
                    withContext(Dispatchers.Main) {
                        callback.onResult(response)
                    }
                } else {
                    // Handle non-OK response codes
                    withContext(Dispatchers.Main) {
                        callback.onError("HTTP error: $responseCode")
                    }
                }
                connection.disconnect()

            } catch (e: Exception) {
                // Handle any other exceptions
                withContext(Dispatchers.Main) {
                    callback.onError("An error occurred: ${e.message}")
                }
            }
        }
    }

    // Function for sending OTP request
    fun changePassword(email: String,password: String, callback: LoginCallback) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL(domain + "changepassword/?email=$email&password=$password")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.doOutput = true

                val responseCode = connection.responseCode
                println("Login request response code: $responseCode")

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }
                    println("Login request response: $response")

                    // Return the result via callback
                    withContext(Dispatchers.Main) {
                        callback.onResult(response)
                    }
                } else {
                    // Handle non-OK response codes
                    withContext(Dispatchers.Main) {
                        callback.onError("HTTP error: $responseCode")
                    }
                }
                connection.disconnect()

            } catch (e: Exception) {
                // Handle any other exceptions
                withContext(Dispatchers.Main) {
                    callback.onError("An error occurred: ${e.message}")
                }
            }
        }
    }

    // Function for validating email request
    fun validadeemail(email: String, callback: LoginCallback) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL(domain + "verifyemail/?email=$email")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.doOutput = true

                val responseCode = connection.responseCode
                println("Login request response code: $responseCode")

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }
                    println("Login request response: $response")

                    // Return the result via callback
                    withContext(Dispatchers.Main) {
                        callback.onResult(response)
                    }
                } else {
                    // Handle non-OK response codes
                    withContext(Dispatchers.Main) {
                        callback.onError("HTTP error: $responseCode")
                    }
                }
                connection.disconnect()

            } catch (e: Exception) {
                // Handle any other exceptions
                withContext(Dispatchers.Main) {
                    callback.onError("An error occurred: ${e.message}")
                }
            }
        }
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
