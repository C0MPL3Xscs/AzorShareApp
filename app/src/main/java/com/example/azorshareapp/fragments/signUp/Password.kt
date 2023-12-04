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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Faz inflate do layout para este fragmento
        val view = inflater.inflate(R.layout.signup_password_fragment, container, false)

        // Cria a animação de carregamento
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage("Carregando...")

        // Define listener para o botão "Proximo"
        view.findViewById<View>(R.id.button3).setOnClickListener {

            // Guarda as palavras-passe introduzidas pelo utilizador
            val editText = requireActivity().findViewById<EditText>(R.id.PasswordOne)
            val editText1 = requireActivity().findViewById<EditText>(R.id.PasswordComfirm)
            val password = editText.text.toString()
            val passwordConfirmed = editText1.text.toString()

            // Valida as palavras-passe
            when {
                password.isEmpty() -> error("Palavra-passe não pode ser vazia")
                password != passwordConfirmed -> error("As palavras-passe não coincidem")
                password.length < 8 -> error("A palavra-passe deve ter pelo menos 8 caracteres")
                else -> {

                    // Mostra animação de carregamento
                    progressDialog.setCancelable(false)
                    progressDialog.show()

                    // Salva a palavra-passe e passa para o proximo fragmento
                    finalPassword = password
                    validated()
                }
            }
        }

        return view
    }

    // Passa para o proximo fragmento
    private fun validated() {
        if (activity is SignUp) {
            (activity as SignUp).switchFragment("OTP",finalPassword)
        }
        // Termina a animação
        progressDialog.dismiss()
    }

    // Mostra/altera menssagem de erro
    private fun error(message: String) {
        val editText = requireActivity().findViewById<EditText>(R.id.PasswordOne)
        val editText1 = requireActivity().findViewById<EditText>(R.id.PasswordComfirm)
        val textView = requireActivity().findViewById<TextView>(R.id.PasswordErrorMessage)

        editText.setBackgroundResource(R.drawable.wrong_input)
        editText1.setBackgroundResource(R.drawable.wrong_input)
        textView.text = message
    }
}
