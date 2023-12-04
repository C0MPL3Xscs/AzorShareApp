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

        // Faz inflate do layout para este fragmento
        val view = inflater.inflate(R.layout.forgot_password_email_fragment, container, false)

        // Cria a animação de carregamento
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage("Carregando...")

        // Define listener para o botão "proximo"
        view.findViewById<View>(R.id.FPUbt).setOnClickListener {

            // Guarda o email introduzido
            val editText = activity?.findViewById<EditText>(R.id.EmailField)
            val email = editText?.text.toString()
            enteredEmail = email.orEmpty()
            if (activity is ForgotPassword) {
                (activity as ForgotPassword).setEmail(enteredEmail)
            }

            // Valida o email
            if (email.isNullOrEmpty()) {
                error("Email cannot be empty")
            } else {
                // Mostra a animação de carregamento
                progressDialog.setCancelable(false)
                progressDialog.show()

                // Request para validar email
                val request = REQUESTS()

                request.validadeemail(email, object : REQUESTS.LoginCallback {
                    override fun onResult(response: String): Boolean {
                        val jsonString = response
                        val jsonObject = JSONObject(jsonString)
                        progressDialog.dismiss()
                        // Caso email exista
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

                // Envia um OTP
                if (activity is ForgotPassword) {
                    (activity as ForgotPassword).sendOTP()
                }
            }
        }

        return view
    }

    // Mostra/altera menssagem de erro
    private fun error(message: String) {
        val editText = activity?.findViewById<EditText>(R.id.EmailField)
        val textView = activity?.findViewById<TextView>(R.id.usernameError)

        editText?.setBackgroundResource(R.drawable.wrong_input)
        textView?.text = message
    }

}
