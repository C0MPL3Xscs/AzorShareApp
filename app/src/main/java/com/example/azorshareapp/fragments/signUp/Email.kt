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
import com.example.azorshareapp.utils.RegexUtils
import org.json.JSONObject

class Email : Fragment(){

    // Stores the final email
    private var finalEmail = ""
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.signup_email_fragment, container, false)

        // Create loading animation
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage("Loading...")

        // Set up the click listener for the button
        view.findViewById<View>(R.id.button3).setOnClickListener {
            // Get the email entered by the user
            val editText = requireActivity().findViewById<EditText>(R.id.EmailField)
            val email = editText.text.toString()

            // Store the email
            finalEmail = email

            // Create a JSON object with the email
            val json = JSONObject().apply {
                put("email", email)
            }

            // Validate the email
            if (email.isEmpty() || !RegexUtils.isEmailValid(email)) {
                // Display an error message if the email is invalid
                error("Enter a valid email")
            } else {
                // Show loading animation
                progressDialog.setCancelable(false)
                progressDialog.show()

                TODO() // MAKE REQUEST TO VALIDADE EMAIL
            }
        }

        return view
    }

    // Switch to the next fragment
    private fun onNextButtonClicked(fragment: String) {
        if (activity is SignUp) {
            (activity as SignUp).switchFragment(fragment, finalEmail)
        }
    }

    private fun error(message: String) {
        val editText = requireActivity().findViewById<EditText>(R.id.EmailField)
        val textView = requireActivity().findViewById<TextView>(R.id.EmailError)

        editText.setBackgroundResource(R.drawable.wrong_input)
        textView.text = message
    }
}
