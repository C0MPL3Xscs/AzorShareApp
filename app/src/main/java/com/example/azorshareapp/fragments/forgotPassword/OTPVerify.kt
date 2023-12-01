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

class OTPVerify : Fragment(){

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
                error("Demasiados requests, reinicie a aplicação e tente novamente")
            }
        }

        view.findViewById<View>(R.id.FPCbt).setOnClickListener {
            // Get the code entered by the user
            val editText = activity?.findViewById<EditText>(R.id.CodeField)
            val otp = editText?.text.toString()

            if (otp.isNullOrEmpty()) {
                error("Code can't be empty")
            } else {
                // Show loading animation
                progressDialog.setCancelable(false)
                progressDialog.show()

                val request = REQUESTS()

                var email = ""

                if (activity is ForgotPassword) {
                    email = (activity as ForgotPassword).getEmail()
                }

                request.validateOtp(email, otp, object : REQUESTS.LoginCallback {
                    override fun onResult(response: String): Boolean {
                        val jsonString = response
                        val jsonObject = JSONObject(jsonString)
                        if (jsonObject.getString("rescode") == "0001"){
                            progressDialog.dismiss()
                            if (activity is ForgotPassword) {
                                (activity as ForgotPassword).changePassword()
                            }
                        }else{
                            error("O codigo não corresponde")
                        }
                        return jsonObject.getString("rescode") == "0001"
                    }
                    override fun onError(response: String): Boolean {
                        try {
                            val jsonObject = JSONObject(response)
                            if (jsonObject.getString("rescode") == "0001"){
                                progressDialog.dismiss()
                                if (activity is ForgotPassword) {
                                    (activity as ForgotPassword).changePassword()
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            error("Ocorreu um erro, tente novamente mais tarde")
                            return false
                        }
                        return false
                    }
                })

                progressDialog.dismiss()
            }
        }

        return view
    }

    private fun error(message: String) {
        val editText = activity?.findViewById<EditText>(R.id.CodeField)
        val textView = activity?.findViewById<TextView>(R.id.OTPError)

        editText?.setBackgroundResource(R.drawable.wrong_input)
        textView?.text = message
    }
}
