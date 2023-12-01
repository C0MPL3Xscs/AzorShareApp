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
import com.example.azorshareapp.services.network.REQUESTS
import org.json.JSONException
import org.json.JSONObject

class EmailRecovery : Fragment(){

    private var enteredEmail: String = ""
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.forgot_password_email_fragment, container, false)
        // Create loading animation
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage("Loading...")

        // Set OnClickListener for the "Find Password" button
        view.findViewById<View>(R.id.FPUbt).setOnClickListener {

            // Get the entered email
            val editText = activity?.findViewById<EditText>(R.id.EmailField)
            val email = editText?.text.toString()
            enteredEmail = email.orEmpty()
            if (activity is ForgotPassword) {
                (activity as ForgotPassword).setEmail(enteredEmail)
            }

            // Validate the email
            if (email.isNullOrEmpty()) {
                error("Email cannot be empty")
            } else {
                // Show loading animation
                progressDialog.setCancelable(false)
                progressDialog.show()
                // Send a request to validate the username
                val request = REQUESTS()

                request.validadeemail(email, object : REQUESTS.LoginCallback {
                    override fun onResult(response: String): Boolean {
                        val jsonString = response
                        val jsonObject = JSONObject(jsonString)
                        progressDialog.dismiss()
                        if (jsonObject.getString("rescode") == "0001") {
                            if (activity is ForgotPassword) {
                                (activity as ForgotPassword).otpView()
                            }
                            return true
                        } else {
                            error("Email invalido")
                        }
                        return false
                    }
                    override fun onError(response: String): Boolean {
                        try {
                            val jsonObject = JSONObject(response)
                            progressDialog.dismiss()
                            if (jsonObject.getString("rescode") == "0001") {
                                if (activity is ForgotPassword) {
                                    (activity as ForgotPassword).otpView()
                                }
                                return true
                            } else {
                                error("Email invalido")
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            progressDialog.dismiss()
                            error("Erro, tente novamente mais tarde")
                        }
                        return false
                    }
                })

                // Send an OTP
                if (activity is ForgotPassword) {
                    (activity as ForgotPassword).sendOTP()
                }
            }
        }

        return view
    }

    private fun error(message: String) {
        val editText = activity?.findViewById<EditText>(R.id.EmailField)
        val textView = activity?.findViewById<TextView>(R.id.usernameError)

        editText?.setBackgroundResource(R.drawable.wrong_input)
        textView?.text = message
    }

}
