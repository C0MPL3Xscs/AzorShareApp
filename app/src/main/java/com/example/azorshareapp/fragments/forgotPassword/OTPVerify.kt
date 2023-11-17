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

class OTPVerify : Fragment(), NetworkTaskCallback {

    private lateinit var progressDialog: ProgressDialog
    private var tries = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.comun_opt_fragment, container, false)
        // Create loading animation
        progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Loading...")
        // Set up the button click listener
        view.findViewById<View>(R.id.ResendEmail).setOnClickListener {
            if (tries <= 3) {
                // Resend OPT
                if (activity is ForgotPassword) {
                    (activity as ForgotPassword).sendOTP()
                }
                tries++
            } else {
                error("Too many requests, restart the app to try again")
            }
        }

        view.findViewById<View>(R.id.FPCbt).setOnClickListener {
            // Get the code entered by the user
            val editText = activity?.findViewById<EditText>(R.id.CodeField)
            val otp = editText?.text.toString()

            // Create a JSON object with the code
            val json = JSONObject()
            try {
                json.put("otp", otp)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            if (otp.isNullOrEmpty()) {
                error("Code can't be empty")
            } else {
                // Show loading animation
                progressDialog.setCancelable(false)
                progressDialog.show()
                // Send a network request to validate the code
                val networkTask = NetworkTask(this@OTPVerify, json.toString(), "validateOTP")
                networkTask.execute()
            }
        }

        return view
    }

    override fun onNetworkTaskComplete(result: Boolean, jsonResponse: String) {
        // Close loading animation
        progressDialog.dismiss()
        if (result) {
            try {
                // Parse the response JSON
                val jsonObject = JSONObject(jsonResponse)
                val header = jsonObject.getJSONObject("header")
                val resCode = header.getString("resCode")
                if (resCode == "00000") {
                    if (activity is ForgotPassword) {
                        // If the code is valid, move on to the next step of password recovery
                        (activity as ForgotPassword).changePassword()
                    }
                } else if (resCode == "00003") {
                    error("Code is not valid")
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        } else {
            // Notify the user that the network request failed
            println("Network request failed")
        }
    }

    private fun error(message: String) {
        val editText = activity?.findViewById<EditText>(R.id.CodeField)
        val textView = activity?.findViewById<TextView>(R.id.OTPError)

        editText?.setBackgroundResource(R.drawable.wrong_input)
        textView?.text = message
    }
}
