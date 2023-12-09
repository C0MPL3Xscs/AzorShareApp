package com.example.azorshareapp.fragments.main_screen.profile

import android.R
import android.R.attr.button
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.azorshareapp.activities.Main
import com.example.azorshareapp.services.network.REQUESTS
import org.json.JSONObject


class Profile : Fragment() {

    var token = ""
    var pictureUrl = ""
    var username = ""
    var followers = ""
    var following = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout first
        val rootView = inflater.inflate(com.example.azorshareapp.R.layout.main_profile_fragment, container, false)

        if (activity is Main) {
            this.token = (activity as Main).getToken()
        }

        val loadingProgressBar = rootView.findViewById<ProgressBar>(com.example.azorshareapp.R.id.loadingProgressBar)
        loadingProgressBar.visibility = View.VISIBLE


        val request = REQUESTS()

        request.getUserData(token, object : REQUESTS.LoginCallback {
            @SuppressLint("MissingInflatedId")
            override fun onResult(response: String): Boolean {
                val jsonString = response

                try {
                    val jsonObject = JSONObject(jsonString)
                    pictureUrl = jsonObject.getString("profilePic")
                    username = jsonObject.getString("username")
                    followers = jsonObject.getString("followers")
                    following = jsonObject.getString("following")

                    rootView.findViewById<TextView>(com.example.azorshareapp.R.id.Usernametxt).text = username
                    rootView.findViewById<TextView>(com.example.azorshareapp.R.id.Followers).text = followers + " SEGUIDORES"
                    rootView.findViewById<TextView>(com.example.azorshareapp.R.id.Following).text = "SEGUINDO " + following
                    rootView.findViewById<Button>(com.example.azorshareapp.R.id.Followbt).text = "EDITAR"

                    Glide.with(requireContext())
                        .load(pictureUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(rootView.findViewById(com.example.azorshareapp.R.id.ProfilePic))

                    loadingProgressBar.visibility = View.GONE // Hide loading indicator after loading data
                } catch (e: Exception) {
                    Log.e("Tag", "Error processing JSON or loading image: $e")
                    loadingProgressBar.visibility = View.GONE // Hide loading indicator in case of an error
                }

                return true
            }

            override fun onError(response: String): Boolean {
                Log.e("Tag", "Error in network request: $response")
                loadingProgressBar.visibility = View.GONE // Hide loading indicator in case of an error
                return false
            }
        })
        val button = rootView.findViewById<Button>(com.example.azorshareapp.R.id.button4)

        button.setOnClickListener {
            showPopupMenu(it)
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Additional setup if needed
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menuInflater.inflate(com.example.azorshareapp.R.menu.popup, popupMenu.menu)

        // Explicitly specify the lambda parameter
        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                com.example.azorshareapp.R.id.menu_item_1 -> {
                    if (activity is Main) {
                        (activity as Main).OpenSettings()
                    }
                    true
                }
                com.example.azorshareapp.R.id.menu_item_2 -> {
                    if (activity is Main) {
                        (activity as Main).logOUt()
                    }
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }
}
