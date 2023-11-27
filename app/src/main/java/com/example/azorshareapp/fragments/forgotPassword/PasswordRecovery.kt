package com.example.azorshareapp.fragments.forgotPassword

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.azorshareapp.R
import org.json.JSONException
import org.json.JSONObject

class PasswordRecovery : Fragment(){

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

        TODO() //MAKE REQUEST TO RECOVERY PASSWORD
    }

    private fun error(message: String) {
        val editText = activity?.findViewById<EditText>(R.id.PasswordField)
        val textView = activity?.findViewById<TextView>(R.id.PasswordError)

        editText?.setBackgroundResource(R.drawable.wrong_input)
        textView?.text = message
    }
}
