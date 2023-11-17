package com.example.azorshareapp.models

import com.google.gson.Gson
import org.json.JSONObject

class AccountModel(
    val email: String,
    val username: String,
    val password: String
) {
    var firstName: String? = null
    var middleName: String? = null
    var lastName: String? = null
    var isBusiness: String = "0"
    var isPublic: String = "1"

    init {
        toJSON()
    }

    fun toJSON(): String {
        val gson = Gson()
        return gson.toJson(this)
    }
}
