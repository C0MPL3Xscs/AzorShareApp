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
import org.json.JSONObject

class Username : Fragment(){

    // Declare instance variables
    private lateinit var finalUsername: String
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.signup_username_fragment, container, false)

        // Create loading animation
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage("Loading...")

        // Set an onClickListener for the button in the layout
        view.findViewById<View>(R.id.button5).setOnClickListener {
            // Get references to the UI elements for username input and error message display
            val editText = requireActivity().findViewById<EditText>(R.id.Username)
            val errorTextView = requireActivity().findViewById<TextView>(R.id.UsernameError)

            // Get the username input from the user
            val username = editText.text.toString()

            // Create a JSON object to hold the username input
            val json = JSONObject().apply {
                put("username", username)
            }

            // If the username input is empty, display an error message
            if (username.isEmpty()) {
                error("Username cannot be empty")
            } else {
                // Show loading animation
                progressDialog.setCancelable(false)
                progressDialog.show()

                finalUsername = username

                TODO() //MAKE REQUEST TO VALIDADE USERNAME
            }
        }

        // Return the view for this fragment
        return view
    }

    // Method to switch to the next fragment when the current one is complete
    private fun changeFragment(fragment: String) {
        if (activity is SignUp) {
            (activity as SignUp).switchFragment(fragment, finalUsername)
        }
    }

    private fun error(message: String) {
        val editText = requireActivity().findViewById<EditText>(R.id.Username)
        val textView = requireActivity().findViewById<TextView>(R.id.UsernameError)

        editText.setBackgroundResource(R.drawable.wrong_input)
        textView.text = message
    }
}
