package com.example.azorshareapp.fragments.signUp

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.azorshareapp.R
import com.example.azorshareapp.activities.SignUp
import com.example.azorshareapp.services.network.NetworkTask
import com.example.azorshareapp.services.network.NetworkTaskCallback
import org.json.JSONException
import org.json.JSONObject

class OTP : Fragment(), NetworkTaskCallback {

    private lateinit var progressDialog: ProgressDialog
    private var tries = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.comun_opt_fragment, container, false)

        // Create loading animation
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage("Loading...")

        view.findViewById<View>(R.id.ResendEmail).setOnClickListener {
            if (tries <= 3) {
                // Resend OPT
                if (activity is SignUp) {
                    (activity as SignUp).sendOTP()
                }
                tries++
            } else {
                error("Too many requests, restart the app to try again")
            }
        }

        // Set the click listener for the button
        view.findViewById<View>(R.id.FPCbt).setOnClickListener {
            // Get the entered OTP code
            val editText = requireActivity().findViewById<EditText>(R.id.CodeField)
            val code = editText.text.toString()

            // Create JSON object with the entered code
            val json = JSONObject().apply {
                put("otp", code)
            }

            if (code.isEmpty()) {
                error("Please insert the code to continue")
            } else {
                // Show loading animation
                progressDialog.setCancelable(false)
                progressDialog.show()

                // Send a network request to validate the OTP code
                val networkTask = NetworkTask(this@OTP, json.toString(), "validateOTP")
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
                val jsonObject = JSONObject(jsonResponse)
                val header = jsonObject.getJSONObject("header")
                val resCode = header.getString("resCode")

                when (resCode) {
                    "00000" -> {
                        // If the OTP is valid, finish the sign-up process
                        if (activity is SignUp) {
                            (activity as SignUp).finish()
                        }
                    }

                    "00003" -> error("Invalid Code")
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        } else {
            println("Network request failed")
        }
    }

    private fun error(message: String) {
        val editText = requireActivity().findViewById<EditText>(R.id.CodeField)
        val textView = requireActivity().findViewById<TextView>(R.id.OTPError)

        editText.setBackgroundResource(R.drawable.wrong_input)
        textView.text = message
    }
}
