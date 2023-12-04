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
import org.json.JSONException

class Password : Fragment() {

    private lateinit var finalPassword: String
    private lateinit var progressDialog: ProgressDialog

    // Called when the fragment's view is created
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.signup_password_fragment, container, false)

        // Create loading animation
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage("Loading...")

        // Set up the button click listener
        view.findViewById<View>(R.id.button3).setOnClickListener {
            // Get the password and confirm password fields
            val editText = requireActivity().findViewById<EditText>(R.id.PasswordOne)
            val editText1 = requireActivity().findViewById<EditText>(R.id.PasswordComfirm)

            // Get the values of the password fields
            val password = editText.text.toString()
            val passwordConfirmed = editText1.text.toString()

            // Check if the password fields are empty or if they don't match
            when {
                password.isEmpty() -> error("Password can't be empty")
                password != passwordConfirmed -> error("Passwords do not match")
                password.length < 8 -> error("Password should be at least 8 characters long")
                else -> {
                    // Show loading animation
                    progressDialog.setCancelable(false)
                    progressDialog.show()

                    // Save the password and move to the next fragment
                    finalPassword = password

                    try {
                        validated()
                    } catch (e: JSONException) {
                        throw RuntimeException(e)
                    }
                }
            }
        }

        return view
    }

    // Move to the next fragment
    private fun validated() {
        if (activity is SignUp) {
            (activity as SignUp).switchFragment("OTP",finalPassword)
        }
        // Close loading animation
        progressDialog.dismiss()
    }

    private fun error(message: String) {
        val editText = requireActivity().findViewById<EditText>(R.id.PasswordOne)
        val editText1 = requireActivity().findViewById<EditText>(R.id.PasswordComfirm)
        val textView = requireActivity().findViewById<TextView>(R.id.PasswordErrorMessage)

        editText.setBackgroundResource(R.drawable.wrong_input)
        editText1.setBackgroundResource(R.drawable.wrong_input)
        textView.text = message
    }
}
