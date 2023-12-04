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
import com.example.azorshareapp.activities.ForgotPassword
import com.example.azorshareapp.services.network.REQUESTS
import org.json.JSONException
import org.json.JSONObject

class PasswordRecovery : Fragment(){

    private var finalPassword: String = ""
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Faz inflate do layout para este fragmento
        val view = inflater.inflate(R.layout.forgot_password_password_fragment, container, false)

        // Cria a animação de carregamento
        progressDialog = ProgressDialog(activity)
        progressDialog.setMessage("Carregando...")

        // Define listener para o botão "restaurar"
        view.findViewById<View>(R.id.FPPbt).setOnClickListener {

            // Guarda a palavra-passe introduzida pelo utilizador
            val editText = activity?.findViewById<EditText>(R.id.PasswordField)
            val password = editText?.text.toString()

            // Valida a palavra-passe
            if (password.isNullOrEmpty()) {
                error("palavra-passe não pode ser vazia")
            } else if (password.length < 8) {
                error("palavra-passe deve ter pelo menos 8 caracteres")
            } else {

                // Mostra animação carregamento
                progressDialog.setCancelable(false)
                progressDialog.show()

                // Guarda e altera a palavra-passe
                finalPassword = password
                changePassword()
            }
        }

        return view
    }

    // Faz um request para alterar a palavra-passe
    private fun changePassword() {

        val request = REQUESTS()

        var email = ""

        if (activity is ForgotPassword) {
            email = (activity as ForgotPassword).getEmail()
        }

        request.changePassword(email, finalPassword, object : REQUESTS.LoginCallback {
            override fun onResult(response: String): Boolean {
                val jsonString = response
                val jsonObject = JSONObject(jsonString)
                if (jsonObject.getString("rescode") == "0001"){
                    progressDialog.dismiss()
                    activity?.finish()
                }else{
                    progressDialog.dismiss()
                    error("Ocorreu um erro, tente novamente mais tarde")
                }
                return jsonObject.getString("rescode") == "0001"
            }
            override fun onError(response: String): Boolean {
                error("Ocorreu um erro, tente novamente mais tarde")
                return false
            }
        })
    }

    // Mostra/altera menssagem de erro
    private fun error(message: String) {
        val editText = activity?.findViewById<EditText>(R.id.PasswordField)
        val textView = activity?.findViewById<TextView>(R.id.PasswordError)

        editText?.setBackgroundResource(R.drawable.wrong_input)
        textView?.text = message
    }
}
