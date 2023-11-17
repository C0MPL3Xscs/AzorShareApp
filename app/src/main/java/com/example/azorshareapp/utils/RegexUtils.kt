package com.example.azorshareapp.utils

import java.util.regex.Pattern

object RegexUtils {

        fun isEmailValid(email: String): Boolean {
        val pat = Pattern.compile(ConstantsUtils.REGEXEMAIL)
        return email?.let { pat.matcher(it).matches() } ?: false
        }
        }
