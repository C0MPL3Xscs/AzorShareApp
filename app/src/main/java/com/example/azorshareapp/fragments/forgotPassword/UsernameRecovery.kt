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

class UsernameRecovery : Fragment(), NetworkTaskCallback {

    private var enteredUsername: String = ""
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.forgot_password_username_fragment, container, false)
        // Create loading animation
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage("Loading...")
        // Set OnClickListener for the "Find Password" button
        view.findViewById<View>(R.id.FPUbt).setOnClickListener {
            // Get the entered username
            val editText = activity?.findViewById<EditText>(R.id.UsernameField)
            val username = editText?.text.toString()
            enteredUsername = username.orEmpty()
            if (activity is ForgotPassword) {
                (activity as ForgotPassword).setUsername(enteredUsername)
            }

            // Validate the username
            if (username.isNullOrEmpty()) {
                error("Username can not be empty")
            } else {
                // Show loading animation
                progressDialog.setCancelable(false)
                progressDialog.show()
                // Send a network request to validate the username
                val json = JSONObject()
                try {
                    json.put("username", username)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                val networkTask = NetworkTask(this, json.toString(), "signup/validateUsername")
                networkTask.execute()
            }
        }

        return view
    }

    // Callback function for the network request
    override fun onNetworkTaskComplete(result: Boolean, jsonResponse: String) {
        // Close loading animation
        progressDialog.dismiss()
        if (result) {
            try {
                val jsonObject = JSONObject(jsonResponse)
                val header = jsonObject.getJSONObject("header")
                val resCode = header.getString("resCode")
                if (resCode == "00000") {
                    // Username doesn't exist, show an error message
                    if (activity is ForgotPassword) {
                        error("Username does not exist")
                    }
                } else if (resCode == "00005") {
                    // Send an OTP email to the user
                    val json2 = JSONObject()
                    json2.put("username", enteredUsername)
                    val opt = NetworkTask(this, json2.toString(), "sendOTP")
                    opt.execute()
                    // Change the view to the OTP screen
                    if (activity is ForgotPassword) {
                        (activity as ForgotPassword).otpView()
                    }
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        } else {
            // Show an error message
            println("Network request failed")
        }
    }

    private fun error(message: String) {
        val editText = activity?.findViewById<EditText>(R.id.UsernameField)
        val textView = activity?.findViewById<TextView>(R.id.usernameError)

        editText?.setBackgroundResource(R.drawable.wrong_input)
        textView?.text = message
    }
}
