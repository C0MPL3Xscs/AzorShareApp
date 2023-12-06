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

    // Request para login
    fun login(email: String, password: String, callback: LoginCallback) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL(domain + "login/?email=$email&password=$password")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.doOutput = true

                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }

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

    // Request para enviar OTP
    fun sendOtp(email: String, callback: LoginCallback) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL(domain + "sendotp/?email=$email")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.doOutput = true

                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }

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
    fun generateToken(email: String, callback: LoginCallback) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL(domain + "generateToken/?email=$email")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.doOutput = true

                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }

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

    // Request para validar OTP
    fun validateOtp(email: String, otp: String, callback: LoginCallback) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL(domain + "verifyotp/?email=$email&otp=$otp")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.doOutput = true

                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }

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

    // Request para validar LoginToken
    fun validadeToken(token: String, callback: LoginCallback) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL(domain + "validatetoken/?email=$token")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.doOutput = true

                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }

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

    // Request para alterar palavra-passe
    fun changePassword(email: String,password: String, callback: LoginCallback) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL(domain + "changepassword/?email=$email&password=$password")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.doOutput = true

                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }

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

    // Request para validar email
    fun validadeemail(email: String, callback: LoginCallback) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL(domain + "verifyemail/?email=$email")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.doOutput = true

                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }

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


    // Request para criar conta
    fun createAccount(username: String, email: String, password: String, callback: LoginCallback) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL(domain + "createaccount/?username=$username&email=$email&password=$password")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.doOutput = true

                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Read the response
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = reader.use { it.readText() }

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
}
