package com.example.azorshareapp.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.azorshareapp.R

class AccountFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Faz inflate do layout para este fragmento
        val view = inflater.inflate(R.layout.settings_account_fragment, container, false)

        return view
    }

}