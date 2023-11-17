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

class Username : Fragment(), NetworkTaskCallback {

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

                // Otherwise, save the username and make a network request to validate it
                finalUsername = username
                val networkTask =
                    NetworkTask(this@Username, json.toString(), "signup/validateUsername")
                networkTask.execute()
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

    // Callback method called when the network request is complete
    override fun onNetworkTaskComplete(result: Boolean, jsonResponse: String) {
        // Close loading animation
        progressDialog.dismiss()

        // If the network request is successful, process the response
        if (result) {
            try {
                val jsonObject = JSONObject(jsonResponse)
                val header = jsonObject.getJSONObject("header")
                val resCode = header.getString("resCode")

                // If the response code indicates success, switch to the next fragment
                if (resCode == "00000") {
                    changeFragment("Password")
                } else if (resCode == "00005") {
                    // If the response code indicates that the username already exists, display an error message
                    error("Username already exists")
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        } else {
            // If the network request fails, print an error message to the console
            println("Network request failed")
        }
    }

    private fun error(message: String) {
        val editText = requireActivity().findViewById<EditText>(R.id.Username)
        val textView = requireActivity().findViewById<TextView>(R.id.UsernameError)

        editText.setBackgroundResource(R.drawable.wrong_input)
        textView.text = message
    }
}
