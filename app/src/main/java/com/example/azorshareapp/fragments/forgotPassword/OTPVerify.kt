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

        // Faz inflate do layout para este fragmento
        val view = inflater.inflate(R.layout.comun_opt_fragment, container, false)

        // Cria a animação de carregamento
        progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Carregando...")

        // Define listener para o botão "ResendEmail"
        view.findViewById<View>(R.id.ResendEmail).setOnClickListener {
            if (tries <= 3) {
                // Envia um OTP novamento um maximo de 3 vezes
                if (activity is ForgotPassword) {
                    (activity as ForgotPassword).sendOTP()
                }
                tries++
            } else {
                error("Demasiados requests, reinicie a aplicação e tente novamente")
            }
        }

        // Define listener para o botão "proximo"
        view.findViewById<View>(R.id.FPCbt).setOnClickListener {

            // Guarda o codigo introduzido pelo utilizador
            val editText = activity?.findViewById<EditText>(R.id.CodeField)
            val otp = editText?.text.toString()

            if (otp.isNullOrEmpty()) {
                error("Codigo não pode ser vazio")
            } else {
                // Mostra animação de carregamento
                progressDialog.setCancelable(false)
                progressDialog.show()

                val request = REQUESTS()

                var email = ""

                if (activity is ForgotPassword) {
                    email = (activity as ForgotPassword).getEmail()
                }

                // Request para validar OTP com o servidor
                request.validateOtp(email, otp, object : REQUESTS.LoginCallback {
                    override fun onResult(response: String): Boolean {
                        val jsonString = response
                        val jsonObject = JSONObject(jsonString)
                        // Caso o codigo introduzido esteja correto
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

    // Mostra/altera menssagem de erro
    private fun error(message: String) {
        val editText = activity?.findViewById<EditText>(R.id.CodeField)
        val textView = activity?.findViewById<TextView>(R.id.OTPError)

        editText?.setBackgroundResource(R.drawable.wrong_input)
        textView?.text = message
    }
}
