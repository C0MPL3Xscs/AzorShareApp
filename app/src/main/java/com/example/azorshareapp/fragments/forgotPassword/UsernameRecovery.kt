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
import org.json.JSONException
import org.json.JSONObject

class UsernameRecovery : Fragment(){

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

                TODO() //MAKE REQUEST TO VALIDATE USERNAME

                TODO() //MAKE REQUEST TO SEND OTP IF USERNAME EXISTS
            }
        }

        return view
    }

    private fun error(message: String) {
        val editText = activity?.findViewById<EditText>(R.id.UsernameField)
        val textView = activity?.findViewById<TextView>(R.id.usernameError)

        editText?.setBackgroundResource(R.drawable.wrong_input)
        textView?.text = message
    }
}
