package com.example.azorshareapp.fragments.forgotPassword

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.azorshareapp.activities.ForgotPassword
import com.example.azorshareapp.R
import com.example.azorshareapp.services.network.NetworkTask
import com.example.azorshareapp.services.network.NetworkTaskCallback
import org.json.JSONException
import org.json.JSONObject

class PasswordRecovery : Fragment(), NetworkTaskCallback {

    private var finalPassword: String = ""
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.forgot_password_password_fragment, container, false)
        // Create loading animation
        progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Loading...")
        // Set up click listener for password recovery button
        view.findViewById<View>(R.id.FPPbt).setOnClickListener {
            // Get the password from the input field
            val editText = activity?.findViewById<EditText>(R.id.PasswordField)
            val password = editText?.text.toString()

            // Check if the password is empty
            if (password.isNullOrEmpty()) {
                error("Password can not be empty")
            } else if (password.length < 8) {
                // Show error message
                error("Password should be at least 8 characters long")
            } else {
                // Show loading animation
                progressDialog.setCancelable(false)
                progressDialog.show()
                // Store the password and proceed to validate it
                finalPassword = password
                validated()
            }
        }

        return view
    }

    // Validate the password with the server
    private fun validated() {
        val json = JSONObject()
        try {
            json.put("password", finalPassword)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val networkTask = NetworkTask(this, json.toString(), "recoverPassword")
        networkTask.execute()
    }

    override fun onNetworkTaskComplete(result: Boolean, jsonResponse: String) {
        // Close loading animation
        progressDialog.dismiss()
        if (result) {
            try {
                // Parse the JSON response from the server
                val jsonObject = JSONObject(jsonResponse)
                val header = jsonObject.getJSONObject("header")
                val resCode = header.getString("resCode")

                // Check if the password was successfully recovered
                if (resCode == "00000") {
                    if (activity is ForgotPassword) {
                        // Finish the password recovery process if the password was successfully recovered
                        (activity as ForgotPassword).finish()
                    }
                } else if (resCode == "00003") {
                    error("An error has occurred, please try again in some minutes")
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        } else {
            // Display an error message if the network request failed
            println("Network request failed")
        }
    }

    private fun error(message: String) {
        val editText = activity?.findViewById<EditText>(R.id.PasswordField)
        val textView = activity?.findViewById<TextView>(R.id.PasswordError)

        editText?.setBackgroundResource(R.drawable.wrong_input)
        textView?.text = message
    }
}
