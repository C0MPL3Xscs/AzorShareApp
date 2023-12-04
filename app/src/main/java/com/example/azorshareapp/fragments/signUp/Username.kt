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
import org.json.JSONObject

class Username : Fragment(){

    private lateinit var finalUsername: String
    private lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Faz inflate do layout para este fragmento
        val view = inflater.inflate(R.layout.signup_username_fragment, container, false)

        // Cria a animação de carregamento
        progressDialog = ProgressDialog(requireActivity())
        progressDialog.setMessage("Loading...")

        // Define listener para o botão "Proximo"
        view.findViewById<View>(R.id.button5).setOnClickListener {

            // Get references to the UI elements for username input and error message display
            val editText = requireActivity().findViewById<EditText>(R.id.Email)
            val username = editText.text.toString()

            // If the username input is empty, display an error message
            if (username.isEmpty()) {
                error("Nome de utilizador não pode ser vazio")
            } else {
                // Show loading animation
                progressDialog.setCancelable(false)
                progressDialog.show()

                finalUsername = username

                // Passa para o proximo fragment
                if (activity is SignUp) {
                    (activity as SignUp).switchFragment("Password",username)
                }
                progressDialog.dismiss()
            }
        }

        return view
    }

    // Mostra/altera menssagem de erro
    private fun error(message: String) {
        val editText = requireActivity().findViewById<EditText>(R.id.Email)
        val textView = requireActivity().findViewById<TextView>(R.id.UsernameError)

        editText.setBackgroundResource(R.drawable.wrong_input)
        textView.text = message
    }
}
